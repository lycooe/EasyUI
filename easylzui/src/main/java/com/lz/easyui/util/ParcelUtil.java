package com.lz.easyui.util;


import com.lz.easyui.EasyUI;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParcelUtil {
    private static final int CACHE_SIZE = 101;
    private String id = null;
    private static Class arrr = null;
    private Map<String, Integer> cache = null;

//    public ParcelUtil(String id, Context ctxt) {
//        this.id = id.replace('.', '_').replace('-', '_');
//
//        try {
//            this.arrr = Class.forName(ctxt.getPackageName() + ".R");
//        } catch (Throwable t) {
//            throw new RuntimeException("Exception finding R class", t);
//        }
//
//        this.cache = Collections.synchronizedMap(new Cache());
//    }

    public static int[] getStyleableArray(String name) {
        try {
            Class clazz = getResourceClass("styleable");

            if (clazz != null) {
                Field fld = clazz.getDeclaredField(name);

                if (fld != null) {
                    Object o = fld.get(clazz);

                    if (o instanceof int[]) {
                        int[] result = new int[Array.getLength(o)];

                        for (int i = 0; i < Array.getLength(o); i++) {
                            result[i] = Array.getInt(o, i);
                        }

                        return (result);
                    }
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException("Exception finding styleable", t);
        }

        return (new int[0]);
    }

    public static int getStyleableId(String component, String attr) {
//        return (getIdentifier(component + "_" + attr, "styleable", false));
        return (getIdentifier(component + "_" + attr, "styleable"));
    }

    public static int getLayoutId(String layout) {
//        return (getIdentifier(layout, "layout", true));
        return (getIdentifier(layout, "layout"));
    }

    public static int getItemId(String item) {
//        return (getIdentifier(item, "id", false));
        return (getIdentifier(item, "id"));
    }

    public static int getMenuId(String menu) {
//        return (getIdentifier(menu, "menu", true));
        return (getIdentifier(menu, "menu"));
    }

    public static int getAnimId(String anim) {
        return (getIdentifier(anim, "anim"));
    }

    public static int getDrawableId(String drawable) {
        return (getIdentifier(drawable, "drawable"));
    }

    public static int getAttrId(String attr) {
        return (getIdentifier(attr, "attr"));
    }

    public static int getStringId(String str) {
        return (getIdentifier(str, "string"));
    }

    public static int getColorId(String color) {
        return (getIdentifier(color, "color"));
    }

    public static int getDimenId(String dimen) {
        return (getIdentifier(dimen, "dimen"));
    }

    public static int getIdentifier(String name, String defType) {
        return EasyUI.resource.getIdentifier(name, defType, EasyUI.pkgName);
    }

    private static Class getResourceClass(String defType) {
//        for (Class clazz : arrr.getClasses()) {
        try {
            for (Class clazz : Class.forName(EasyUI.pkgName + ".R").getClasses()) {
                if (defType.equals(clazz.getSimpleName())) {
                    return (clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (null);
    }

    public class Cache extends LinkedHashMap<String, Integer> {
        public Cache() {
            super(CACHE_SIZE, 1.1f, true);
        }

        protected boolean removeEldestEntry(Entry eldest) {
            return (size() > CACHE_SIZE);
        }
    }
}
