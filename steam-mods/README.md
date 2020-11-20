# Steam-mods downloader

This program takes steam mods identifications (numerical id) as an input. 
Then it tries to download and extract those mods into correct mod folder.
Eventual mod installation and management should be done using official mod managers such as Paradox's launcher.

Stellaris is currenty only game supported.

## Term definition

Let's define some terms, that are used across docs and source code.

* ```game``` Video game such as Stellaris, Crusader Kings 2, ...
* ```mod``` Game modding. Games can be user-modified. Extension of how much depends on a game support. Paradox games are generally pretty generous for user-modifications (mods). There are many web sites  sharing of user modifications such as Nexus.com. However, this program concentrates on Steam Workshop mods only so far.
* ```operation``` This program performs many "operations". The operation as such is defined as a single atomic operation, that this program can perform. Such as 1] downloading mods from web, 2] extracting all files in folder or 3] restructuring (copying, moving files around) game mod folder, ...
* ```chain``` Chain is strictly ordered list of operations. When chain is executed, operations are performed one-by-one... Therefore, chain could be more correctly called "ChainOfOperations".


## Configuration file

This program can be configured in json based configuration file. ```configuration.json```. File structure is explained in this section.

```
{
  "app" : {
    "current_game_id": "1"
  },
  "games": [ 
    {
      "id": "1",
      "name": "Stellaris",
      "description": "",
      "default_mod_folder" : "%25USERPROFILE%25/Documents",
      "default_chain_id": "CHAIN_1",
      "chosen_mod_folder" : "%25USERPROFILE%25/Documents/Test",
      "selected_chain_id" : "CHAIN_1",
      "managed_mod_ids": [
          1712760331,
          2219070393,
          ...
      ]
    },
    {
      ...
    }
  ],
  "operations" : [ 
    {
      "id" : "OPERATION_1",
      "name" : "Operation 1 - Extract all file",
      "description" : "Extract all .zip files in the given folder.",
      "class" : "model.operations.ExtractAllOperation"
    },
    {
      ...
    }
  ],
  "chains" : [ 
    {
      "id" : "CHAIN_1",
      "name" : "Basic workshop downloader",
      "description" : "Basic chain"
      "operation_ids" : [ 
        "OPERATION_1", 
        "OPERATION_3", 
        "OPERATION_99"
      ]
    },
    {
      ...
    }
  ]
}
```


## User guide
* Ensure that selenium web-drivers are up-to-date with your browser (only firefox and chrome browsers are supported...). Re-place those drivers into ```drivers/``` folder. Alternatively you can place those drivers whenever you want. In that case you must update paths in program configuration file accordingly.
    - Firefox: ```https://github.com/mozilla/geckodriver/releases```
    - Chrome: ```https://chromedriver.chromium.org/downloads```
* Collect requested mods ids.

    - There is a Stellaris game mod called ```~ StarNet AI```.
    Steams web page for this mod is: https://steamcommunity.com/sharedfiles/filedetails/?id=1712760331
    "~ Starnet AI" id should be easily recognized as "id" parameter of the given url as ```1712760331```.
* Enter mod ids into program configuration file.
* Start the program and confirm/choose mods to download and game's mod folder to download to and install mods to.

    - The Stellaris game's mod folder is on Windows platform located in the following folder: ```%USERPROFILE%\Documents\Paradox Interactive\Stellaris\mod\```
    

## Developer guide

Any Steam mod is collection of files. Specifically mod consists of "it's" mod folder and a single "descriptor.mod" file.
Descriptor.mod file must be located on the same level as mod folder in a file hierarchy.

```
<mod-folder-location> (%USERPROFILE%\Documents\Paradox Interactive\Stellaris\mod\)
    ├── <mod-1-starnet-ai-folder>
    │    ├── <mod-1-files-folders>
    │    └── descriptor.mod
    ├── <mod-2-another-folder>
    │    ├── <mod-2-files-folders>
    │    └── descriptor.mod
    ├── mod-1-starnet.mod (copied, but renamed descriptor.mod)
    └── mod-2-another.mod (copied, but renamed descriptor.mod)
```



This program works in the following strict order: 
1. Determine required input variables from configuration file. Such as web drivers paths, mod download folder, required mod ids. Any of those can be supplied in the program UI.
2. Try to download mods from website: ```https://steamworkshopdownloader.io/``` using Selenium framework. 
Mods are downloaded into a mod folder specified by user in configuration file and/or in UI,
3. Programs performs "Operations" in the mod folder in sequential order. These "Operations" are:

    3.1. Extracting all downloaded mods (Mod is downloaded as a single .zip archive).
    3.2. 
