package plasmapuffs.oneitem.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jspecify.annotations.Nullable;

@Mixin(GuiGraphicsExtractor.class)
public class GuiGraphicsExtractorMixin {
	@Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
	private void itemCount(final Font font, final ItemStack itemStack, final int x, final int y, final @Nullable String countText, CallbackInfo callbackInformation) {
		GuiGraphicsExtractor guiGraphicsExtractor = (GuiGraphicsExtractor)(Object)this;

		// Only draw the item count (1) when there is no given custom count text and when the item is singular and stackable
		if (countText == null && itemStack.getCount() == 1 && itemStack.isStackable()) {
			String amount = String.valueOf(itemStack.getCount());
			guiGraphicsExtractor.text(font, amount, x + 19 - 2 - font.width(amount), y + 6 + 3, -1, true);
			callbackInformation.cancel();
		}
	}
}
