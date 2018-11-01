package com.kx.behavior.zoomimagebehavior.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.kx.behavior.zoomimagebehavior.R;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2018/11/1.
 */
public class ZoomImageBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private WeakReference<View> dependentView;

    private ImageView zoomImage;

    public ZoomImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getDependentViewCollapsedHeight()));
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.zoomImageHeader) {
            dependentView = new WeakReference<>(dependency);
            zoomImage = dependency.findViewById(R.id.iv_header);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        System.out.println(zoomImage.getTop());
        child.setTranslationY(dependency.getHeight());
        child.setAlpha(1);
        return true;
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }
    private View getDependentView() {
        return dependentView.get();
    }
    private float getDependentViewCollapsedHeight() {
        return getDependentView().getResources().getDimension(R.dimen.dp300);
    }
}
