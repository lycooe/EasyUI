package com.lz.easyui.util;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class RelayoutViewTool {

    public static void relayoutViewWithScale(View view, float scale) {

        if (view == null) {
            return;
        }

        resetViewLayoutParams(view, scale);

        if (view instanceof ViewGroup) {
            View[] where = null;
            try {
                Field field = ViewGroup.class.getDeclaredField("mChildren");
                field.setAccessible(true);
                where = (View[]) field.get(view);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (where != null) {
                for (View child : where) {
                    relayoutViewWithScale(child, scale);
                }
            }
        }
    }

    private static void resetViewLayoutParams(View view, float scale) {
		
		if(view instanceof TextView){
			resetTextSize((TextView) view, scale);
		}

		int pLeft = convertFloatToInt(view.getPaddingLeft() *  scale);
		int pTop = convertFloatToInt(view.getPaddingTop() *  scale);
		int pRight = convertFloatToInt(view.getPaddingRight() * scale);
		int pBottom = convertFloatToInt(view.getPaddingBottom() * scale);
		view.setPadding(pLeft,pTop,pRight,pBottom);

		LayoutParams params = view.getLayoutParams();
		scaleLayoutParams(params, scale);
		
	}
    
    private static void scaleLayoutParams(LayoutParams params, float scale){
		if (params == null) {
			return;
		}
		if (params.width > 0) {
			params.width = convertFloatToInt(params.width  * scale);
		}
		if (params.height > 0) {
			params.height = convertFloatToInt(params.height  * scale);
		}

		if (params instanceof MarginLayoutParams) {
			MarginLayoutParams mParams = (MarginLayoutParams) params;
			if (mParams.leftMargin > 0) {
				mParams.leftMargin = convertFloatToInt(mParams.leftMargin * scale);
			}
			if (mParams.rightMargin > 0) {
				mParams.rightMargin = convertFloatToInt(mParams.rightMargin * scale);
			}
			if (mParams.topMargin > 0) {
				mParams.topMargin = convertFloatToInt(mParams.topMargin * scale);
			}
			if (mParams.bottomMargin > 0) {
				mParams.bottomMargin = convertFloatToInt(mParams.bottomMargin * scale);
			}
		}
	}


    @SuppressLint("NewApi")
	private static void resetTextSize(TextView textView, float scale) {
		float size = textView.getTextSize();
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * scale);
        float spacingExtra = 0;
        float spacingMultip = 1;
        try {
            spacingExtra = textView.getLineSpacingExtra();
            spacingMultip = textView.getLineSpacingMultiplier();
        } catch (NoSuchMethodError e) {

        }
        textView.setLineSpacing(spacingExtra * scale, spacingMultip);
	}

    //float 转换至 int 小数四舍五入
    private static int convertFloatToInt(float sourceNum) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }
}
