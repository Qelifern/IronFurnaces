package ironfurnaces.capability;

public class PlayerShowConfig implements IPlayerShowConfig {

    public int value;

    public PlayerShowConfig(int value)
    {
        this.value = value;
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public int set(int value) {
        return this.value = value;
    }
}
