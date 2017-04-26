<?php

/**
 * Load specific config file. Overwrite the default configuration with your
 * local configuration by copying the config/default.php config file and rename 
 * it to config/config.php (this file is in .gitignore!).
 *
 * If no config/config.php file is present, the application will not work!!
 *
 *
 * IMPORTANT: Never check in your real config.php file. Everyone can view it on
 *            Github!!
 *
 */


// first try to load config based on environment
if (file_exists(__dir__ . "/config/config.php")) {
  require __dir__ . "/config/config.php";
}
else {
  die('No config file found!');
}

?>
