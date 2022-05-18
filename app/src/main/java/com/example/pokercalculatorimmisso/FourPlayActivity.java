package com.example.pokercalculatorimmisso;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pokercalculatorimmisso.adapter.CardAdapter;
import com.example.pokercalculatorimmisso.databinding.ActivityFourPlayBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FourPlayActivity extends Activity {

    ObjectAnimator animation;
    MyCardView aceClubs;

    ActivityFourPlayBinding binding;
    CardAdapter adapter;
    BottomSheetDialog dialog;
    int dpiPixel5 = 440; // dpi моего эмулятора

    private ViewGroup mainLayout;

    private boolean suitable = false; // переменная флаг
    int number; // номер места под карту из массива, чтобы запоминать i из цикла
    private int xDelta;
    private int yDelta;
    private int xBegin; // последняя статичная координата Х объекта, которую я запоминаю при начале передвижения этого объекта
    private int yBegin; // последняя статичная координата У объекта, которую я запоминаю при начале передвижения этого объекта
    int[] posXY = new int[2];
    int xCard = posXY[0];
    int yCard = posXY[1];
    private final int cardLength = 270; // длина карты in pixels
    private final int cardWidth = 200; // ширина карты in pixels
    int [][] matrix= { // массив координат в пикселях, взятых из paint, для каждого места под карту (5 игроков по 2 карты, 4 масти, 5 в центре)
            {37, 251, 475, 696, 910, 1129, 1357, 1575, 227, 473, 727, 1049, 1375, 37, 251, 684, 912, 1357, 1586}, // координаты x от левого края
            {41, 41, 63, 63, 63, 63, 41, 41, 365, 365, 365, 365, 365, 717, 717, 707, 702, 702, 702} // координаты y от верхнего края
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // начался жизненный цикл онКриэйт
        super.onCreate(savedInstanceState); // наследование функций и методов онКриэйта
        setContentView(R.layout.activity_four_play); //привязка к XML-файла к активити
        mainLayout = findViewById(R.id.four_play); // вводим новый объект, которая будет отвечать за XML файл массива
        MyCardView aceDiamonds = findViewById(R.id.ace_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceClubs = findViewById(R.id.ace_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceSpades = findViewById(R.id.ace_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceHearts = findViewById(R.id.ace_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла

        Path path = new Path(); // создаем объект (путь) типа Path
        path.moveTo(251f, 41f); // и перемещаем его по таким координатам (взяты вторые координаты из массива)

        animation = ObjectAnimator.ofFloat(aceClubs, View.X, View.Y, path); // связываем вместе объект, который нам нужно подвинуть и путь, и передвигаем их по ранее заданным координатам
        animation.start(); // запускаем процесс передвиженя по координатам


        aceDiamonds.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceClubs.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceSpades.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceHearts.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут

        aceDiamonds.getLocationOnScreen(posXY);
    }

    @Override
    protected void onStart() { // жизненный цикл онСтарт
        super.onStart(); // наследование функций и методов онСтарта
    }

    protected int transformationDpToPx(int dp){ // метод который преобразовывает dp в px, но он неидеален, так как dpi, которая используется в расчетах не константа для всех смартфонов
        int px = dp * (dpiPixel5/160); //формула найдена в интернете
        return px; // метод возвращает пиксельное значение
    };

    protected int transformationPxToDp(int px){ //обратный метод
        int dp = px * (160 / dpiPixel5); //формула выведена из предыдущей
        return dp; // метод возвращает значение dp
    };
    private View.OnTouchListener onTouchListener() { // метод, который ждет пока нажмут на объект
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) { // метод, который обрабатывает нажатие на объект

                final int x = (int) event.getRawX(); //координата х где нажалась кнопка мыши
                final int y = (int) event.getRawY(); //координата у где нажалась кнопка мыши

                switch (event.getAction() & MotionEvent.ACTION_MASK) { // рассматриваем три случая, которые необходимы для передвижения объекта (кнопка нажата -- движение -- кнопка отжата)

                    case MotionEvent.ACTION_DOWN: // случай, когда кнопка мыши НАЖАТА
                        ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) // создаю слой, по координатам которого будет двигаться объект
                                view.getLayoutParams();

                        if (view.getVisibility() == View.VISIBLE ) { // двигает только, когда объект видимый (можно использовать будет в дальнейшем, когда будет куча объектов и лишь некоторые будут видимыми)
                            xDelta = x - lParams.leftMargin; //внутренние переменные: xDelta YDelta это не переменные моего объекта, а переменные в программе
                            yDelta = y - lParams.topMargin; // xDelta yDelta это разницы координат где нажалась мышка и левой верхней координатой карты
                            xBegin = lParams.leftMargin; // здесь мы запоминаем координату Х, когда мышка нажимает на объект
                            yBegin = lParams.topMargin; // здесь мы запоминаем координату У, когда мышка нажимает на объект
                        }
                        break;

                    case MotionEvent.ACTION_UP: // случай, когда кнопка мыши ОТЖАТА
                        suitable = false; // обозначаем переменную флаг ложью, чтобы далее использовать ее значение (тру или фолз)
                        for (int i = 0; i <= 18; i++) {

                            if ((matrix[0][i] <= x) && (x <= matrix[0][i] + cardWidth) && (matrix[1][i] <= y) && (y <= matrix[1][i]+ cardLength)) {  // условия для попадания координаты мыши/карты в нужный прямоугольник
                                suitable = true; //тут я в сомнениях, ставить тру или фолз, потому что фолз на фолз даст тру или фолз просто поменяется на тру
                                number = i; //благодаря этой переменной я могу использовать i вне цикла
                                break; //прерываем цикл, хотя возможно в этой строке нет необходимости
                            }
                        }

                        if (suitable = true) { //теперь пытаюсь делать переставление объекта после отжатия клавишы, когда флаг тру, но не получается. объект просто двигается по экрану
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                            .getLayoutParams();
                                layoutParams.leftMargin = matrix[0][number]; // ставить на координаты такие-то (взяты из массива). использована та переменная, связанная i
                               layoutParams.topMargin = matrix[1][number];
                                layoutParams.rightMargin = 0;
                                layoutParams.bottomMargin = 0;
                                view.setLayoutParams(layoutParams);
                        } else { //и когда флаг фолз. не работает ни то ни другое
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                        .getLayoutParams();
                                layoutParams.leftMargin = xBegin; //ставить на прошлые зафиксированные координаты
                                layoutParams.topMargin = yBegin;
                                layoutParams.rightMargin = 0;
                                layoutParams.bottomMargin = 0;
                                view.setLayoutParams(layoutParams);
                    }
                        break;

                    case MotionEvent.ACTION_MOVE: // случай, когда объект движется (а движется он только тогда, когда кнопка мыши НАЖАТА и не была ОТЖАТА с прошлого раза)

                        if (view.getVisibility() == View.VISIBLE ) {
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                    .getLayoutParams();
                            layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = y - yDelta;
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            view.setLayoutParams(layoutParams);
                        }
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }

    //                            else { // что делать если не попал
//                                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
//                                        .getLayoutParams();
//                                layoutParams.leftMargin = xBegin; //ставить на прошлые зафиксированные координаты
//                                layoutParams.topMargin = yBegin;
//                                layoutParams.rightMargin = 0;
//                                layoutParams.bottomMargin = 0;
//                                view.setLayoutParams(layoutParams);
//                                suitable = true;
//                            }

     /*
     это как раз рабочий способ перемещения карты на какую-либо координату на слое
        Path path = new Path();
        path.moveTo(matrix[0][i], matrix[1][i]);

        animation = ObjectAnimator.ofFloat(aceClubs, View.X, View.Y, path);
        animation.start();
         */

     /*
      должны быть в onCreate
            binding = ActivityFourPlayBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            dialog = new BottomSheetDialog(this);
            adapter = new CardAdapter(getCard(), this);
            binding.centerList.setAdapter(adapter);
            binding.selectCardClub.setOnClickListener(view -> {
                showBottomDialog();
            });
    */

//    private void placeImage(float X, float Y) {
//        int touchX = (int) X;
//        int touchY = (int) Y;
//
//        // placing at bottom right of touch
//        touchView2.layout(touchX, touchY, touchX+48, touchY+48);
//
//        //placing at center of touch
//        int viewWidth = touchView2.getWidth();
//        int viewHeight = touchView2.getHeight();
//        viewWidth = viewWidth / 2;
//        viewHeight = viewHeight / 2;
//
//        touchView2.layout(touchX - viewWidth, touchY - viewHeight, touchX + viewWidth, touchY + viewHeight);
//    }
}


/*
    должно быть внутри программы
    private void showBottomDialog() {

    }

    private ArrayList<CardModel> getCard() {

        ArrayList<CardModel> cards = new ArrayList<>();
        cards.add(new CardModel("", 0, R.drawable.ace_clubs));
        cards.add(new CardModel("", 0, R.drawable.ace_diamonds));
        cards.add(new CardModel("", 0, R.drawable.ace_hearts));
        cards.add(new CardModel("", 0, R.drawable.ace_spades));
        cards.add(new CardModel("", 0, R.drawable.queen_clubs));
        cards.add(new CardModel("", 0, R.drawable.queen_diamonds));
        cards.add(new CardModel("", 0, R.drawable.queen_hearts));
        cards.add(new CardModel("", 0, R.drawable.queen_spades));

        return cards;
    }
*/