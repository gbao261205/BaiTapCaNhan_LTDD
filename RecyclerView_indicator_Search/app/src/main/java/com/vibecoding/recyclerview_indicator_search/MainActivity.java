package com.vibecoding.recyclerview_indicator_search;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rclIcon;
    private IconAdapter iconAdapter;
    private List<IconModel> list;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rclIcon = findViewById(R.id.rclcon);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        list = getListIcon();
        iconAdapter = new IconAdapter(this, list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rclIcon.setLayoutManager(gridLayoutManager);

        rclIcon.setAdapter(iconAdapter);

        // Add pager snapping
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rclIcon);

        // Add the indicator decoration
        rclIcon.addItemDecoration(new LinePagerIndicatorDecoration());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }

    private void filterListener(String text) {
        List<IconModel> filteredList = new ArrayList<>();
        for (IconModel iconModel : list) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(iconModel);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setListenerList(filteredList);
        }
    }

    private List<IconModel> getListIcon() {
        list = new ArrayList<>();
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 1"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 2"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 3"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 4"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 5"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 6"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 7"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 8"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 9"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 10"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 11"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 12"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 13"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 14"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 15"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 16"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 17"));
        list.add(new IconModel(R.drawable.ic_baseline_person_24, "Icon 18"));
        return list;
    }

    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

        private final int colorActive = 0xFFFFFFFF;
        private final int colorInactive = 0x66FFFFFF;

        private final float DP = getResources().getDisplayMetrics().density;

        private final int mIndicatorHeight = (int) (DP * 16);

        private final float mIndicatorStrokeWidth = DP * 2;

        private final float mIndicatorItemLength = DP * 16;

        private final float mIndicatorItemPadding = DP * 4;

        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();

        public LinePagerIndicatorDecoration() {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int itemCount = parent.getAdapter().getItemCount();

            float totalLength = mIndicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            final View activeChild = layoutManager.findViewByPosition(activePosition);
            if (activeChild == null) {
                return;
            }
            int left = activeChild.getLeft();
            int width = activeChild.getWidth();

            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
        }

        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
            mPaint.setColor(colorInactive);

            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            float start = indicatorStartX;
            for (int i = 0; i < itemCount; i++) {
                c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
                start += itemWidth;
            }
        }

        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                    int highlightPosition, float progress, int itemCount) {
            mPaint.setColor(colorActive);

            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            if (progress == 0F) {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
            } else {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                float partialLength = mIndicatorItemLength * progress;

                c.drawLine(highlightStart + partialLength, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);

                if (highlightPosition < itemCount - 1) {
                    highlightStart += itemWidth;
                    c.drawLine(highlightStart, indicatorPosY,
                            highlightStart + partialLength, indicatorPosY, mPaint);
                }
            }
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mIndicatorHeight;
        }
    }
}
