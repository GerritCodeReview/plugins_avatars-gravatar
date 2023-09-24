Configuration
=============

The configuration of the @PLUGIN@ plugin is done in the `gerrit.config`
file.

```
  [plugin "@PLUGIN@"]
    type = identicon
```

- `plugin.@PLUGIN@.type`: Avatar type in www.gravatar.com service.

	Can be any type supported by the service
	(404, mm, identicon, monsterid, wavatar, retro, robohash, blank, ...).

	Default: identicon

- `plugin.@PLUGIN@.rating`: Avatar rating in www.gravatar.com service.

	Can be any rating supported by the service
	(g, pg, r, x, ...).

	Default: pg

- `plugin.@PLUGIN@.gravatarUrl`: URL that provides the Gravatar API.

	Uses provided protocol (http, https) or the same as gerrit.canonicalWebUrl.

	Default: www.gravatar.com/avatar/

- `plugin.@PLUGIN@.changeAvatarUrl`: Link to where the avatar can be changed as displayed in the profile settings.

	Default: http://www.gravatar.com

- `plugin.@PLUGIN@.defaultImage`: Whether to have a default image placeholder when the account does not have
	a default email.

	Default: true
