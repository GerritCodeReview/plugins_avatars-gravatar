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
:	Avatar type in www.gravatar.com service.

	Can be any type supported by the service 
        (404, mm, identicon, monsterid, wavatar, retro, blank, ...).
        