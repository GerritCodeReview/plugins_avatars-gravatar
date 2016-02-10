Configuration
=============

The configuration of the @PLUGIN@ plugin is done in the `gerrit.config`
file.

```
  [plugin "@PLUGIN@"]
    type = identicon
```

<a id="type">
`plugin.@PLUGIN@.type`
:   Avatar type in www.gravatar.com service.

    Can be any type supported by the service
    (404, mm, identicon, monsterid, wavatar, retro, blank, ...).

    Default: identicon

<a id="gravatarUrl">
`plugin.@PLUGIN@.gravatarUrl`
:   URL that provides the Gravatar API.

    Uses the same protocol (http, https) as gerrit.canonicalWebUrl.

    Default: www.gravatar.com/avatar/

<a id="changeUrl">
`plugin.@PLUGIN@.changeUrl`
:   Link to where the avatar can be changed as displayed in the profile settings.

    Default: http://www.gravatar.com
