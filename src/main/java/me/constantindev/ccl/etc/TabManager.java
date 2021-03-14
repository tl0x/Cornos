package me.constantindev.ccl.etc;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.ms.Tab;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TabManager {

    private HashMap<MType, Tab> tabs;
    private ArrayList<MType> tabType;
    private int currentTab;
    private HashMap<MType, ArrayList<Module>> mods;
    private HashMap<MType, Integer> currentMods;

    public TabManager() {
        tabs = new HashMap<>();
        tabType = new ArrayList<>();
        currentTab = 0;
        mods = new HashMap<>();
        currentMods = new HashMap<>();
        for (MType mType : MType.values()) {
            mods.put(mType, new ArrayList<>());
            currentMods.put(mType, 0);
            tabs.put(mType, new Tab());
            tabType.add(mType);
        }
        for (Module m : ModuleRegistry.getAll()) {
            ArrayList<Module> modsList = mods.get(m.type);
            modsList.add(m);
        }
    }
    public HashMap<MType, Tab> getTabs() {
        return tabs;
    }
    public HashMap<MType, ArrayList<Module>> getMods() {
        return mods;
    }
    public HashMap<MType, Integer> getCurrentMods() {
        return currentMods;
    }
    public int getCurrentTab() {
        return currentTab;
    }
    public ArrayList<MType> getTabType() {
        return tabType;
    }

    public void keyPressed(int k) {
        switch (k) {
            case 265: // Up Arrow
                if (tabs.get(tabType.get(currentTab)).isExpanded()) {
                    int i = currentMods.get(tabType.get(currentTab));
                    if (i != 0) {
                        i--;
                        currentMods.put(tabType.get(currentTab), i);
                    }
                } else {
                    if (currentTab != 0) {
                        currentTab--;
                    }
                }
                break;
            case 264: // Down Arrow
                if (tabs.get(tabType.get(currentTab)).isExpanded()) {
                    int i = currentMods.get(tabType.get(currentTab));
                    if (i <= mods.get(tabType.get(currentTab)).size() - 2) {
                        i++;
                        currentMods.put(tabType.get(currentTab), i);
                    }
                } else {
                    if (currentTab <= mods.get(tabType.get(currentTab)).size() - 1) {
                        currentTab++;
                    }
                }
                break;
            case 262: // Right Arrow
                if (tabs.get(tabType.get(currentTab)).isExpanded()) {
                    Module m = mods.get(tabType.get(currentTab)).get(currentMods.get(tabType.get(currentTab)));
                    m.isOn.toggle();
                } else {
                    tabs.get(tabType.get(currentTab)).setExpanded(true);
                }
                break;
            case 263: // Left Arrow
                if (tabs.get(tabType.get(currentTab)).isExpanded()) {
                    tabs.get(tabType.get(currentTab)).setExpanded(false);
                }
                break;
        }

    }
}
