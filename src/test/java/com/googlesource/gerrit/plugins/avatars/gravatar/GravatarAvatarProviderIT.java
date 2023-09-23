// Copyright (C) 2023 The Android Open Source Project
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

import static com.google.common.truth.Truth.assertThat;

import com.google.gerrit.acceptance.LightweightPluginDaemonTest;
import com.google.gerrit.acceptance.TestPlugin;
import com.google.gerrit.acceptance.UseLocalDisk;
import com.google.gerrit.acceptance.config.GerritConfig;
import com.google.gerrit.entities.Account;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.PropertyMap;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.junit.Test;

@UseLocalDisk
@TestPlugin(
    name = "avatars-gravatar",
    sysModule =
        "com.googlesource.gerrit.plugins.avatars.gravatar.GravatarAvatarProviderIT$TestModule")
public class GravatarAvatarProviderIT extends LightweightPluginDaemonTest {
  private static final int IMAGE_SIZE = 100;

  @Inject IdentifiedUser.GenericFactory userFactory;

  public static class TestModule extends AbstractModule {}

  @Test
  public void accountWithoutEmailShouldHaveImageByDefault() {
    assertThat(gravatarAvatar().getUrl(testUserWithoutEmail(), IMAGE_SIZE)).isNotNull();
  }

  @Test
  @GerritConfig(name = "plugin.avatars-gravatar.defaultImage", value = "false")
  public void accountWithoutEmailShouldDisableDefaultImage() {
    assertThat(gravatarAvatar().getUrl(testUserWithoutEmail(), IMAGE_SIZE)).isNull();
  }

  private GravatarAvatarProvider gravatarAvatar() {
    GravatarAvatarProvider gravatar =
        plugin.getSysInjector().getInstance(GravatarAvatarProvider.class);
    return gravatar;
  }

  private IdentifiedUser testUserWithoutEmail() {
    IdentifiedUser testUser = userFactory.forTest(Account.id(1), PropertyMap.EMPTY);
    return testUser;
  }
}
