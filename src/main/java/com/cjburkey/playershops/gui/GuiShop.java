package com.cjburkey.playershops.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.playershops.EconHandler;
import com.cjburkey.playershops.LanguageHandler;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.inventory.GuiHandler;
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
	private InvGuiItem[] clickableItems;
	
	public GuiShop(PlayerShop shop, Inventory inv, Player ply, int page) {
		this.shop = shop;
		this.inv = inv;
		this.ply = ply;
		this.page = page;
		clickableItems = new InvGuiItem[(ShopHandler.SHOP_ROWS + 1) * 9];
	}
	
	public void open() {
		Set<Entry<ItemStack, ShopItemData>> items = shop.getItems(page * ShopHandler.SHOP_ROWS * 9, (page + 1) * ShopHandler.SHOP_ROWS * 9).entrySet();
		int i = 0;
		for (Entry<ItemStack, ShopItemData> entry : items) {
			ItemStack stack = buildStack(entry.getKey(), entry.getValue());
			inv.setItem(i, stack);
			clickableItems[i] = new InvGuiItem(stack, (e) -> {
				if (e.isLeftClick()) {
					if (e.isShiftClick()) {
						EconHandler.tryBuy(ply, entry.getKey(), 64, shop);
						return;
					}
					EconHandler.tryBuy(ply, entry.getKey(), 1, shop);
					return;
				}
				if (e.isRightClick()) {
					if (e.isShiftClick()) {
						EconHandler.trySell(ply, entry.getKey(), 64, shop);
						return;
					}
					EconHandler.trySell(ply, entry.getKey(), 1, shop);
					return;
				}
			});
			i ++;
		}
		if (page > 0) {
			Material prevPage = Material.BARRIER;
			ItemStack prev = new ItemStack(prevPage, 1);
			ItemMeta meta = prev.getItemMeta();
			meta.setDisplayName(Util.color(LanguageHandler.get("prevPage")));
			prev.setItemMeta(meta);
			i = inv.getSize() - 9;
			inv.setItem(i, prev);
			clickableItems[i] = new InvGuiItem(prev, (e) -> {
				if (e.isLeftClick()) {
					GuiHandler.close(ply);
					ShopHandler.showShop(shop.getOwner(), ply, page - 1);
				}
			});
		}
		if (page < (int) Math.ceil(((double) shop.getItems().size() / (double) (ShopHandler.SHOP_ROWS * 9))) - 1) {
			Material nextPage = Material.FEATHER;
			ItemStack next = new ItemStack(nextPage, 1);
			ItemMeta meta = next.getItemMeta();
			meta.setDisplayName(Util.color(LanguageHandler.get("nextPage")));
			next.setItemMeta(meta);
			i = inv.getSize() - 1;
			inv.setItem(i, next);
			clickableItems[i] = new InvGuiItem(next, (e) -> {
				if (e.isLeftClick()) {
					GuiHandler.close(ply);
					ShopHandler.showShop(shop.getOwner(), ply, page + 1);
				}
			});
		}
	}
	
	public void click(InventoryClickEvent e) {
		e.setCancelled(true);
		ItemStack stack = e.getCurrentItem();
		if (stack == null) {
			return;
		}
		for (InvGuiItem item : clickableItems) {
			if (item == null) {
				continue;
			}
			if (item.getStack().equals(stack)) {
				item.click(e.getClick());
				return;
			}
		}
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
			Util.color(LanguageHandler.get("guiPrices")),
			Util.color(LanguageHandler.getFormat("guiBuyPrice", ((data.getBuy() < 0.0d) ? "Disabled" : data.getBuy()))),
			Util.color(LanguageHandler.getFormat("guiSellPrice", ((data.getSell() < 0.0d) ? "Disabled" : data.getSell()))),
			Util.color(LanguageHandler.getFormat("guiStock", ((data.getStock() > 0) ? data.getStock() : "None"))),
		});
	}
	
}