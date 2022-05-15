package ironfurnaces.util;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import net.minecraft.nbt.CompoundTag;

public class FurnaceSettings {
    public int[] settings;
    public int[] autoIO;
    public int[] redstoneSettings;
    public int augmentGUI;
    public int autoSplit;

    public FurnaceSettings() {
        settings = new int[]{0, 0, 0, 0, 0, 0};
        autoIO = new int[]{0, 0};
        // (mode 1, 2, 3, 4, subtract) ignored, low/high, comparator, comparator sub, subtract
        redstoneSettings = new int[]{0, 0};
        augmentGUI = 0;
        autoSplit = 0;
    }

    public int get(int index) {
        try {
            switch (index) {
                case 0:
                    return settings[0];
                case 1:
                    return settings[1];
                case 2:
                    return settings[2];
                case 3:
                    return settings[3];
                case 4:
                    return settings[4];
                case 5:
                    return settings[5];
                case 6:
                    return autoIO[0];
                case 7:
                    return autoIO[1];
                case 8:
                    return redstoneSettings[0];
                case 9:
                    return redstoneSettings[1];
                case 10:
                    return augmentGUI;
                case 11:
                    return autoSplit;
                default:
                    return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (Config.showErrors.get()) {
                IronFurnaces.LOGGER.error("Something went wrong.");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
                }
            }
        }
        return 0;
    }

    public void set(int index, int value) {
        try {
            switch (index) {
                case 0:
                    settings[0] = value;
                    break;
                case 1:
                    settings[1] = value;
                    break;
                case 2:
                    settings[2] = value;
                    break;
                case 3:
                    settings[3] = value;
                    break;
                case 4:
                    settings[4] = value;
                    break;
                case 5:
                    settings[5] = value;
                    break;
                case 6:
                    autoIO[0] = value;
                    break;
                case 7:
                    autoIO[1] = value;
                    break;
                case 8:
                    redstoneSettings[0] = value;
                    break;
                case 9:
                    redstoneSettings[1] = value;
                    break;
                case 10:
                    augmentGUI = value;
                    break;
                case 11:
                    autoSplit = value;
                    break;
                default:
                    break;
            }
            onChanged();
        } catch (ArrayIndexOutOfBoundsException e) {
            if (Config.showErrors.get()) {
                IronFurnaces.LOGGER.error("Something went wrong.");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
                }
            }
        }
    }

    public int size() {
        return settings.length + autoIO.length + redstoneSettings.length + 2;
    }

    public void read(CompoundTag tag) {
        this.settings = tag.getIntArray("Settings");
        this.autoIO = tag.getIntArray("AutoIO");
        this.redstoneSettings = tag.getIntArray("Redstone");
        this.augmentGUI = tag.getInt("AugmentGUI");
        this.autoSplit = tag.getInt("AutoSplit");
        onChanged();
    }

    public void write(CompoundTag tag) {
        tag.putIntArray("Settings", settings);
        tag.putIntArray("AutoIO", autoIO);
        tag.putIntArray("Redstone", redstoneSettings);
        tag.putInt("AugmentGUI", augmentGUI);
        tag.putInt("AutoSplit", autoSplit);
    }

    public void onChanged() {

    }
}