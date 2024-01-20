# NaurelliaModeration - Changelog

## Moderation Plugin for NaurelliaCraft
### Author : Armotik
### API Version 1.20.4

---

```
## [1.20.4] - 2024-01-21
### Added
- Add a new command : /chatfilter (to enable/disable/edit the chat filter)
- Add a new command : /raidmode (to enable/disable the raid mode)
- Add a new feature : AntiSpam (to prevent spam in the chat)
- Add a new feature : AntiLinks (to prevent links in the chat)
- Add a new Event : AsyncPlayerPreLoginEvent (to prevent players to join the server if the raid mode is enabled)

### Changed
- Finish the report system
- Modify the ChatFilter class to handle the new /chatfilter command and the new events
- Modify the SanctionManager class to handle the null value of staff member for warns and temporary mutes
- Modify the FilesReader class to handle write new blacklist words in the blacklistedWords.txt file

### Removed
- ChatFilter events from the EventManager class (now handled by the ChatFilter class)
- blacklistedWords.txt file from the resources folder -> now in the server folder
```