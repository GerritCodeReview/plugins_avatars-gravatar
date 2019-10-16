// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.avatars.gravatar;

import com.google.gerrit.extensions.annotations.Listen;
import com.google.gerrit.extensions.annotations.PluginName;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.avatar.AvatarProvider;
import com.google.gerrit.server.config.CanonicalWebUrl;
import com.google.gerrit.server.config.PluginConfigFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Listen
@Singleton
public class GravatarAvatarProvider implements AvatarProvider {

  private static final char[] HEX = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
  };

  private static String hex(byte[] in) {
    StringBuilder r = new StringBuilder(2 * in.length);
    for (byte b : in) {
      r.append(HEX[(b & 0xf0) >>> 4]).append(HEX[b & 0xf]);
    }
    return r.toString();
  }

  private final boolean ssl;
  private final String avatarType;
  private final String avatarRating;
  private final String gravatarUrl;
  private final String changeAvatarUrl;

  @Inject
  GravatarAvatarProvider(
      @CanonicalWebUrl String canonicalUrl,
      @PluginName String pluginName,
      PluginConfigFactory cfgFactory) {
    ssl = canonicalUrl.startsWith("https://");
    this.avatarType = cfgFactory.getFromGerritConfig(pluginName).getString("type", "identicon");
    this.avatarRating = cfgFactory.getFromGerritConfig(pluginName).getString("rating", "pg");
    this.gravatarUrl =
        cfgFactory
            .getFromGerritConfig(pluginName)
            .getString("gravatarUrl", "www.gravatar.com/avatar/");
    this.changeAvatarUrl =
        cfgFactory
            .getFromGerritConfig(pluginName)
            .getString("changeAvatarUrl", "http://www.gravatar.com");
  }

  @Override
  public String getUrl(IdentifiedUser forUser, int imageSize) {
    if (forUser.getAccount().preferredEmail() == null) {
      return null;
    }
    final String email = forUser.getAccount().preferredEmail().trim().toLowerCase();
    final byte[] emailMd5;
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      emailMd5 = digest.digest(email.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("JVM lacks UTF-8 encoding", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 digest not supported - required for Gravatar");
    }
    StringBuilder url = new StringBuilder();
    if (ssl) {
      url.append("https://");
    } else {
      url.append("http://");
    }
    url.append(gravatarUrl);
    url.append(hex(emailMd5));
    url.append(".jpg");
    url.append("?d=" + avatarType + "&r=" + avatarRating);
    if (imageSize > 0) {
      url.append("&s=").append(imageSize);
    }
    return url.toString();
  }

  @Override
  public String getChangeAvatarUrl(IdentifiedUser forUser) {
    return changeAvatarUrl;
  }
}
