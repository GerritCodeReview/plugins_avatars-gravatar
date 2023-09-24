Gerrit Gravatar plugin
======================

This plugin populates Gerrit users' avatar images from the popular Gravatar platform using the MD5
code of the user's preferred email.

How to build
------------

Clone or link this plugin to the plugins directory of Gerrit's source tree,
and then run bazel build on the plugin's directory.

Example:

```
git clone --recursive https://gerrit.googlesource.com/gerrit
cd plugins
git clone "https://gerrit.googlesource.com/modules/avatars-gravatar"
cd .. && bazelisk build plugins/avatars-gravatar
```

The output plugin jar is created in:

```
bazel-bin/plugins/avatars-gravatar/avatars-gravatar.jar

How to install
--------------

Copy the avatars-gravatar.jar into the `${GERRIT_SITE}/plugins/` and start
Gerrit Code Review.

Configuration
-------------

The plugin configuration is read from $GERRIT_SITE/etc/gerrit.config, see
the [CONFIG.md](src/main/resources/Documentation/config.md) documentation for
more details on the individual settings.
