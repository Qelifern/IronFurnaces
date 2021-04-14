package ironfurnaces.energy;

import net.minecraftforge.energy.EnergyStorage;

public class HeaterEnergyStorage extends EnergyStorage {

    public HeaterEnergyStorage(int capacity) {
        super(capacity);
    }

    public HeaterEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public HeaterEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public HeaterEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    protected void onEnergyChanged() {

    }

    public int getEnergy() {
        return this.getEnergyStored();
    }

    public int getCapacity() {
        return this.getMaxEnergyStored();
    }

    public EnergyStorage setCapacity(int capacity) {

        this.capacity = capacity;

        if (energy > capacity) {
            energy = capacity;
        }
        onEnergyChanged();
        return this;
    }

    public EnergyStorage setMaxTransfer(int maxTransfer) {

        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public EnergyStorage setMaxReceive(int maxReceive) {

        this.maxReceive = maxReceive;
        return this;
    }

    public EnergyStorage setMaxExtract(int maxExtract) {

        this.maxExtract = maxExtract;
        return this;
    }

    public int getMaxReceive() {

        return maxReceive;
    }

    public int getMaxExtract() {

        return maxExtract;
    }

    public void setEnergy(int energy) {

        this.energy = energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }
}
