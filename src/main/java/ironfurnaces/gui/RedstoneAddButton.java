package ironfurnaces.gui;

import ironfurnaces.network.Messages;
import ironfurnaces.network.PacketButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class RedstoneAddButton extends AbstractButton {

    BlockPos pos;

    public RedstoneAddButton(int x, int y, int width, int height, ITextComponent title, BlockPos pos) {
        super(x, y, width, height, title);
        this.pos = pos;
    }
    @Override
    public void onPress() {
        Messages.INSTANCE.sendToServer(new PacketButton(pos, 1));
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.active) {
            if (keyCode != 257 && keyCode != 32 && keyCode != 335) {
                return false;
            } else {
                this.playDownSound(Minecraft.getInstance().getSoundHandler());
                this.onPress();
                return true;
            }
        } else {
            return false;
        }
    }
}
