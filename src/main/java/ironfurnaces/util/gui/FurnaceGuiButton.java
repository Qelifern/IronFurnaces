package ironfurnaces.util.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import ironfurnaces.network.Messages;
import ironfurnaces.network.PacketSettingsButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class FurnaceGuiButton {
    public int left;
    public int top;
    public int x;
    public int y;
    public int width;
    public int height;
    public int u;
    public int v;
    public int u_hover;
    public int v_hover;
    public int u_enabled;
    public int v_enabled;

    public FurnaceGuiButton(int left, int top, int x, int y, int width, int height, int u, int v, int u_hover, int v_hover, int u_enabled, int v_enabled)
    {
        this.left = left;
        this.top = top;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.u_hover = u_hover;
        this.v_hover = v_hover;
        this.u_enabled = u_enabled;
        this.v_enabled = v_enabled;
    }

    public FurnaceGuiButton(int left, int top, int x, int y, int width, int height)
    {
        this(left, top, x, y, width, height, -1, -1, -1, -1, -1, -1);
    }

    public FurnaceGuiButton(int left, int top, int x, int y, int width, int height, int u_hover, int v_hover)
    {
        this(left, top, x, y, width, height, -1, -1, u_hover, v_hover, u_hover, v_hover);
    }

    public void changeEnabledUV(int u, int v)
    {
        this.u_enabled = u;
        this.v_enabled = v;
    }

    public void onClick(double mouseX, double mouseY, BlockPos pos, int index, int set, boolean condition)
    {
        if (condition)
        {
            if (hovering(mouseX, mouseY))
            {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(pos, index, set));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 0.6F, 0.3F));
            }
        }
    }

    public void onRightClick(double mouseX, double mouseY, int button, BlockPos pos, int index, int set, boolean condition)
    {
        if (button == GLFW.GLFW_MOUSE_BUTTON_2)
        {
            if (condition)
            {
                if (hovering(mouseX, mouseY))
                {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(pos, index, set));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 0.3F, 0.3F));
                }
            }
        }
    }

    public void render(Screen screen, PoseStack matrix, int mouseX, int mouseY, boolean enabled)
    {

            if (!hovering(mouseX, mouseY) && hasUV())
                screen.blit(matrix, left + x, top + y, u, v, width, height);

            if (hovering(mouseX, mouseY) && hasUVHover())
                screen.blit(matrix, left + x, top + y, u_hover, v_hover, width, height);

            if (enabled && hasUVEnabled())
                screen.blit(matrix, left + x, top + y, u_enabled, v_enabled, width, height);



    }

    public boolean hovering(double mouseX, double mouseY)
    {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void renderTooltip(Screen screen, PoseStack matrix, Component text, int mouseX, int mouseY, boolean condition)
    {
        if (condition)
            if (hovering(mouseX, mouseY))
                screen.renderTooltip(matrix, text, mouseX, mouseY);
    }

    public void renderComponentTooltip(Screen screen, PoseStack matrix, List<Component> text, int mouseX, int mouseY, boolean condition)
    {
        if (condition)
            if (hovering(mouseX, mouseY))
                screen.renderComponentTooltip(matrix, text, mouseX, mouseY);
    }

    public boolean hasUV()
    {
        return u >= 0 && v >= 0;
    }

    public boolean hasUVHover()
    {
        return u_hover >= 0 && v_hover >= 0;
    }

    public boolean hasUVEnabled()
    {
        return u_enabled >= 0 && v_enabled >= 0;
    }

    public static boolean isShiftKeyDown() {
        return isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isKeyDown(int glfw) {
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(glfw);
        int keyCode = key.getValue();
        if (keyCode != InputConstants.UNKNOWN.getValue()) {
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            try {
                if (key.getType() == InputConstants.Type.KEYSYM) {
                    return InputConstants.isKeyDown(windowHandle, keyCode);
                } /**else if (key.getType() == InputMappings.Type.MOUSE) {
                 return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                 }**/
            } catch (Exception ignored) {
            }
        }
        return false;
    }


}
