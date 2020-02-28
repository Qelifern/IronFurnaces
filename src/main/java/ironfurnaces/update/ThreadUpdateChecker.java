package ironfurnaces.update;

import ironfurnaces.IronFurnaces;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * Credits: Ellpeck, creator of the Actually Additions update checker, which I (pizzaatime) modified.
 * Link to the Actually Additions repo: https://github.com/Ellpeck/ActuallyAdditions/
 * Link to the Actually Additions curse page: https://minecraft.curseforge.com/projects/actually-additions
 */
public class ThreadUpdateChecker extends Thread {

    public ThreadUpdateChecker() {
        this.setName(" Iron Furnaces Update Checker");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        IronFurnaces.LOGGER.info("Starting Update Check...");
        try {
            URL newestURL = new URL("https://raw.githubusercontent.com/Qelifern/IronFurnaces/1.15.2/update/updateVersions.properties");
            Properties updateProperties = new Properties();
            updateProperties.load(new InputStreamReader(newestURL.openStream()));

            int highest = 0;
            String highestString = "";

            for(String updateMC : updateProperties.stringPropertyNames()){
                String updateVersion = updateProperties.getProperty(updateMC);
                int update = Integer.parseInt(updateVersion);
                if(highest < update){
                    highest = update;
                    highestString = updateMC+"-release"+updateVersion;
                }
            }

            UpdateChecker.updateVersionInt = highest;
            UpdateChecker.updateVersionString = highestString;

            int clientVersion = Integer.parseInt(IronFurnaces.VERSION);
            if (UpdateChecker.updateVersionInt > clientVersion) {
                UpdateChecker.needsUpdateNotify = true;
            }

            IronFurnaces.LOGGER.info("Update Check done!");
        } catch (Exception e) {
            IronFurnaces.LOGGER.error("Update Check failed!", e);
            UpdateChecker.checkFailed = true;
        }

        if (!UpdateChecker.checkFailed) {
            if (UpdateChecker.needsUpdateNotify) {
                IronFurnaces.LOGGER.info("There is an Update for Iron Furnaces available!");
                IronFurnaces.LOGGER.info("Current Version: " + IronFurnaces.MC_VERSION + "-" + IronFurnaces.VERSION + ", newest Version: " + UpdateChecker.updateVersionString + "!");
                IronFurnaces.LOGGER.info("View the Changelog at " + UpdateChecker.CHANGELOG_LINK);
                IronFurnaces.LOGGER.info("Download at " + UpdateChecker.DOWNLOAD_LINK);
            } else {
                IronFurnaces.LOGGER.info("Iron Furnaces is up to date!");
            }
        }

        UpdateChecker.threadFinished = true;
    }

}
