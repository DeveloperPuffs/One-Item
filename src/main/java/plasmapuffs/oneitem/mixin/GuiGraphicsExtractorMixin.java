package plasmapuffs.oneitem.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jspecify.annotations.Nullable;

@Mixin(GuiGraphicsExtractor.class)
public class GuiGraphicsExtractorMixin {
	@Shadow
	public void text(final Font font, final String str, final int x, final int y, final int color, final boolean dropShadow) {}

	@Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
	private void itemCount(final Font font, final ItemStack itemStack, final int x, final int y, final @Nullable String countText, CallbackInfo callbackInfo) {
		if (itemStack.getCount() == 1 && itemStack.isStackable() && countText == null) {
			String amount = String.valueOf(itemStack.getCount());
			this.text(font, amount, x + 19 - 2 - font.width(amount), y + 6 + 3, -1, true);
			callbackInfo.cancel();
		}
	}
}