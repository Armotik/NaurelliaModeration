# NaurelliaModeration

## Moderation Plugin for NaurelliaCraft
### Author : Armotik
### API Version 1.20.4

---

<ins>This plugin manages the incoming connections to the server :</ins>
- Verification of the player's IP Address
- Check if the player is connected to a VPN
- Check if the player have other account at the same IP Address

<ins>This plugin manages the incoming message sent :</ins>
- Checks for inappropriate language in the message

<ins>This plugin includes GUIS for a better handling of the moderation :</ins>
- Main Menu GUI (Access to all the other GUIS)
- Report GUI (Get the player's information and the reason of the report and be able to treat it)
- Reports GUI (Get all the reports, click on one to open the Report GUI)
- Infractions GUI (Apply a predefine sanction to a player)


---

The server is not available.

You can contact me at `contact@naurellia.com` or on Discord `https://discord.gg/64ncf5HZSQ` 

---

last update : 20/01/2024

&copy; 2024 NaurelliaCraft

```

## [1.20.4] - 2024-01-20
### Added
- Add a new command : /freeze
- Add a new command : /unfreeze
- Add a new command : /invsee
- Add a new command : /openinv
- Add a new command : /report
- Add a new command : /reports
- Add a new command : /staffchat

- Add new GUIS :
    - Moderation Main Menu GUI
    - Report GUI
    - Reports GUI

- Add a new class : Report (for the report system)

### Changed
- Change the SanctionManager class to add the new functions
- Change the Database Utils class to clean the code
- Change the EventManager class to add the new events and handle the new GUIS
- Change the FilesReader class to add read reports

### Removed

```