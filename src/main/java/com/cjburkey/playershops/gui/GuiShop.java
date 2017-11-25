package com.cjburkey.playershops.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.inventory.IInvScreen;
import com.cjburkey.playershops.inventory.InvGuiItem;
import com.cjburkey.playershops.shop.PlayerShop;
import com.cjburkey.playershops.shop.ShopHandler;
import com.cjburkey.playershops.shop.ShopItemData;

public class GuiShop implements IInvScreen {
	
	private final PlayerShop shop;
	private final Inventory inv;
	private final Player ply;
	private final int page;
	private InvGuiItem[] items;
	
	public GuiShop(PlayerShop shop, Inventory inv, Player ply, int page) {
		this.shop = shop;
		this.inv = inv;
		this.ply = ply;
		this.page = page;
		items = new InvGuiItem[ShopHandler.SHOP_ROWS * 9];
	}
	
	public void open() {
		Set<Entry<ItemStack, ShopItemData>> items = shop.getItems(page * ShopHandler.SHOP_ROWS * 9, (page + 1) * ShopHandler.SHOP_ROWS * 9).entrySet();
		int i = 0;
		for (Entry<ItemStack, ShopItemData> entry : items) {
			ItemStack stack = buildStack(entry.getKey(), entry.getValue());
			inv.setItem(i, stack);
			this.items[i] = new InvGuiItem(stack, (e) -> {
				if (e.isLeftClick()) {
					if (e.isShiftClick()) {
						// TODO: TRY BUY 64
						return;
					}
					// TODO: TRY BUY 1
					return;
				}
				if (e.isRightClick()) {
					if (e.isShiftClick()) {
						// TODO: TRY SELL 64
						return;
					}
					// TODO: TRY SELL 1
					return;
				}
			});
			i ++;
		}
	}
	
	public void click(InventoryClickEvent e) {
		
	}
	
	public ItemStack atPos(int x, int y) {
		return null;
	}
	
	public Player getOpener() {
		return ply;
	}
	
	public Inventory getInv() {
		return inv;
	}
	
	private static ItemStack buildStack(ItemStack shopContents, ShopItemData data) {
		ItemStack out = new ItemStack(shopContents);
		out.setAmount(1);
		ItemMeta meta = out.getItemMeta();
		meta.setLore(getLore(data));
		out.setItemMeta(meta);
		return out;
	}
	
	private static List<String> getLore(ShopItemData data) {
		return Arrays.asList(new String[] {
			Util.color("&fPrices:"),
			Util.color("  &2Buy: &l" + ((data.getBuy() < 0.0d) ? "Disabled" : data.getBuy())),
			Util.color("  &cSell: &l" + ((data.getSell() < 0.0d) ? "Disabled" : data.getSell())),
			Util.color("&9In stock: &l" + ((data.getStock() > 0) ? data.getStock() : "None"))
		});
	}
	
}