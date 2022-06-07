package com.example.pokercalculatorimmisso;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pokercalculatorimmisso.adapter.CardAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class FourPlayActivity extends Activity {

    ObjectAnimator animation;
    MyCardView aceClubs;

    //ActivityFourPlayBinding binding;
    CardAdapter adapter;
    BottomSheetDialog dialog;
    private final int dpiPixel5 = 440; // dpi моего эмулятора

    private ViewGroup mainLayout;

    private final int CARD_AMOUNT = 4;
    MyCardView[] CardArray = new MyCardView [CARD_AMOUNT];//Объекты визуальные карт, номер визуальной карты в этом массиве соотвествует значениею элемента массива в IndexInCardArray
    int[] CardScreenPosition = new int [CARD_AMOUNT];//Позиция каждой карты на экране (плюс в каждой позиции на экране, выводится та карта, номер которой в этом массиве больше)
    int[] IndexInCardArray = new int [CARD_AMOUNT];//Номер в массиве - это индекс визуальной карты в массиве CardArray, один и тот же индекс в массивах CardScreenPosition и IndexInCardArray соответствуют одной визуальной карте
    private int oldPosition, newPosition;

    private boolean suitable = false; // переменная флаг для контроля нахождения карты в месте под карту
    private boolean isMove = false; //еще одна переменная флаг для движения
    private int number; // номер места под карту из массива, чтобы запоминать i из цикла
    private int xDel;
    private int yDel;
    private int xDelta;
    private int yDelta;
    private int xBegin = 0; // последняя статичная координата Х объекта, которую я запоминаю при начале передвижения этого объекта
    private int yBegin = 0; // последняя статичная координата У объекта, которую я запоминаю при начале передвижения этого объекта
    private final int cardLength = 284; // длина карты in pixels (270)
    private final int cardWidth = 225; // ширина карты in pixels (200/210)
    int [][] matrix1= { // массив координат в пикселях, взятых из paint, для каждого места под карту (5 игроков по 2 карты, 4 масти, 5 в центре)
            {37, 251, 475, 696, 910, 1129, 1357, 1575, 227, 473, 727, 1049, 1375, 37, 251, 684, 912, 1357, 1586}, // координаты x от левого края
            {41, 41, 63, 63, 63, 63, 41, 41, 365, 365, 365, 365, 365, 717, 717, 707, 702, 702, 702} // координаты y от верхнего края
    };
    public int [][] matrix= new int [2][19]; // массив координат в пикселях, взятых из paint, для каждого места под карту (5 игроков по 2 карты, 4 масти, 5 в центре)
            //{35, 253, 499, 730, 964, 1200, 1434, 1670, 228, 493, 762, 1105, 1452, 35, 253, 720, 958, 1434, 1675}, // координаты x от левого края
            //{35, 35, 58, 58 , 58, 58, 35, 35, 337 , 337 , 337 , 337 , 337 , 665 , 665 , 655 , 650 , 650 , 650} // координаты y от верхнего края
    //массивы которые буду использоваться в экшнаппе для забора координат для массива
    public int[] location = new int[2];
    int[] location0 = new int[2];
    int[] location1 = new int[2];
    int[] location2 = new int[2];
    int[] location3 = new int[2];
    int[] location4 = new int[2];
    int[] location5 = new int[2];
    int[] location6 = new int[2];
    int[] location7 = new int[2];
    int[] location8 = new int[2];
    int[] location9 = new int[2];
    int[] location10 = new int[2];
    int[] location11 = new int[2];
    int[] location12 = new int[2];
    int[] location13 = new int[2];
    int[] location14 = new int[2];
    int[] location15 = new int[2];
    int[] location16 = new int[2];
    int[] location17 = new int[2];
    int[] location18 = new int[2];

    //объявление переменных малевичей
    ImageView black0, black1, black2, black3, black4, black5, black6, black7, black8, black9, black10, black11, black12, black13, black14, black15, black16, black17, black18;
    ImageView white0, white1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // начался жизненный цикл онКриэйт
        super.onCreate(savedInstanceState); // наследование функций и методов онКриэйта
        setContentView(R.layout.activity_four_play); //привязка к XML-файла к активити
        mainLayout = findViewById(R.id.four_play); // вводим новый объект, которая будет отвечать за XML файл массива
        CardArray[0]=findViewById(R.id.ace_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[1]=findViewById(R.id.ace_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[2]=findViewById(R.id.ace_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[3]=findViewById(R.id.ace_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла

        white0 = findViewById(R.id.white0);
        white1 = findViewById(R.id.white1);
        black0 = findViewById(R.id.black0);
        black1 = findViewById(R.id.black1);
        black2 = findViewById(R.id.black2);
        black3 = findViewById(R.id.black3);
        black4 = findViewById(R.id.black4);
        black5 = findViewById(R.id.black5);
        black6 = findViewById(R.id.black6);
        black7 = findViewById(R.id.black7);
        black8 = findViewById(R.id.black8);
        black9 = findViewById(R.id.black9);
        black10 = findViewById(R.id.black10);
        black11 = findViewById(R.id.black11);
        black12 = findViewById(R.id.black12);
        black13 = findViewById(R.id.black13);
        black14 = findViewById(R.id.black14);
        black15 = findViewById(R.id.black15);
        black16 = findViewById(R.id.black16);
        black17 = findViewById(R.id.black17);
        black18 = findViewById(R.id.black18);

        /*
        MyCardView aceDiamonds = findViewById(R.id.ace_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceClubs = findViewById(R.id.ace_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceSpades = findViewById(R.id.ace_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        MyCardView aceHearts = findViewById(R.id.ace_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла

        Path path = new Path(); // создаем объект (путь) типа Path
        path.moveTo(762f, 337f); // и перемещаем его по таким координатам (взяты вторые координаты из массива)

        animation = ObjectAnimator.ofFloat(aceClubs, View.X, View.Y, path); // связываем вместе объект, который нам нужно подвинуть и путь, и передвигаем их по ранее заданным координатам
        animation.start(); // запускаем процесс передвиженя по координатам
*/

        for (int index = 0; index < CARD_AMOUNT; index++) {//Цикл по всем картам на экране
         CardArray[index].setOnTouchListener(onTouchListener()); //привязываем к объекту метод, который ждет, пока на объект не нажмут
          CardArray[index].numb=index;//Устанавливаем внутри объекта карты позицию в массивах IndexInCardArray и CardScreenPosition
          IndexInCardArray[index] = index;
      }
        CardScreenPosition[0]=2;//Diamonds на 3 месте
        CardArray[0].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardScreenPosition[1]=3;//Clubs на 4 месте
        CardArray[1].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardScreenPosition[2]=4;//Spades на 5 месте
        CardArray[2].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardScreenPosition[3]=5;//Hearts на 6 месте
        CardArray[3].suitNumber=3;//Устанавливаем внутри объекта карты масть
/*
        aceDiamonds.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceClubs.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceSpades.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
        aceHearts.setOnTouchListener(onTouchListener()); // привязываем к объекту метод, который ждет, пока на объект не нажмут
 */
    }

    @Override
    protected void onStart() { // жизненный цикл онСтарт
        super.onStart(); // наследование функций и методов онСтарта
    }

    protected int findCardNumber(int Numb) {//Если есть карта в позиции на экране numb, то возвращает индекс этой карты в массиве IndexInCardArray и CardScreenPosition
        int arrin;
        arrin = CARD_AMOUNT + 1;
        for (int index = CARD_AMOUNT - 1; index >= 0; index--) {//Цикл по всем картам на экране
            if (CardScreenPosition[index] == Numb) {  // Если позиция карты на экране является заданной в аргументе функции
                arrin = index; //Запоминаем индекс в массиве для возврата из функции
                break; //прерываем цикл, хотя возможно в этой строке нет необходимости
            }
        }
        return arrin;
    }

        protected int transformationDpToPx ( int dp)
        { // метод который преобразовывает dp в px, но он неидеален, так как dpi, которая используется в расчетах не константа для всех смартфонов
            int px = dp * (dpiPixel5 / 160); //формула найдена в интернете
            return px; // метод возвращает пиксельное значение
        };

        protected int transformationPxToDp ( int px){ //обратный метод
            int dp = px * (160 / dpiPixel5); //формула выведена из предыдущей
            return dp; // метод возвращает значение dp
        };

    private View.OnTouchListener onTouchListener() { // метод, который ждет пока нажмут на объект
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) { // метод, который обрабатывает нажатие на объект

                black0.getLocationOnScreen(location);
                matrix[0][0]=location[0];
                matrix[1][0]=location[1];
                black1.getLocationOnScreen(location);
                matrix[0][1]=location[0];
                matrix[1][1]=location[1];
                black2.getLocationOnScreen(location);
                matrix[0][2]=location[0];
                matrix[1][2]=location[1];
                black3.getLocationOnScreen(location);
                matrix[0][3]=location[0];
                matrix[1][3]=location[1];
                black4.getLocationOnScreen(location);
                matrix[0][4]=location[0];
                matrix[1][4]=location[1];
                black5.getLocationOnScreen(location);
                matrix[0][5]=location[0];
                matrix[1][5]=location[1];
                black6.getLocationOnScreen(location);
                matrix[0][6]=location[0];
                matrix[1][6]=location[1];
                black7.getLocationOnScreen(location);
                matrix[0][7]=location[0];
                matrix[1][7]=location[1];
                black8.getLocationOnScreen(location);
                matrix[0][8]=location[0];
                matrix[1][8]=location[1];
                black9.getLocationOnScreen(location);
                matrix[0][9]=location[0];
                matrix[1][9]=location[1];
                black10.getLocationOnScreen(location);
                matrix[0][10]=location[0];
                matrix[1][10]=location[1];
                black11.getLocationOnScreen(location);
                matrix[0][11]=location[0];
                matrix[1][11]=location[1];
                black12.getLocationOnScreen(location);
                matrix[0][12]=location[0];
                matrix[1][12]=location[1];
                black13.getLocationOnScreen(location);
                matrix[0][13]=location[0];
                matrix[1][13]=location[1];
                black14.getLocationOnScreen(location);
                matrix[0][14]=location[0];
                matrix[1][14]=location[1];
                black15.getLocationOnScreen(location);
                matrix[0][15]=location[0];
                matrix[1][15]=location[1];
                black16.getLocationOnScreen(location);
                matrix[0][16]=location[0];
                matrix[1][16]=location[1];
                black17.getLocationOnScreen(location);
                matrix[0][17]=location[0];
                matrix[1][17]=location[1];
                black18.getLocationOnScreen(location);
                matrix[0][18]=location[0];
                matrix[1][18]=location[1];

                final int x = (int) event.getRawX(); //координата х где нажалась кнопка мыши
                final int y = (int) event.getRawY(); //координата у где нажалась кнопка мыши

                //Toast.makeText(FourPlayActivity.this,  "x="+ x + " y=" + y, Toast.LENGTH_SHORT).show();

                switch (event.getAction() & MotionEvent.ACTION_MASK) { // рассматриваем три случая, которые необходимы для передвижения объекта (кнопка нажата -- движение -- кнопка отжата)

                    case MotionEvent.ACTION_DOWN:// случай, когда кнопка мыши НАЖАТА

                        //Toast.makeText(FourPlayActivity.this,  "x="+ x + " y=" + y, Toast.LENGTH_SHORT).show();

                        //ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) // создаю слой, по координатам которого будет двигаться объект
                        //      view.getLayoutParams();

                        white0.getLocationOnScreen(location0);
                        white1.getLocationOnScreen(location1);

                        if ((view.getVisibility() == View.VISIBLE )&& (isMove == false)) { // двигает только, когда объект видимый (можно использовать будет в дальнейшем, когда будет куча объектов и лишь некоторые будут видимыми)
                            view.getLocationOnScreen(location);
                            xDel = location[0] - x; //внутренние переменные: xDelta YDelta это не переменные моего объекта, а переменные в программе
                            yDel = location[1] - y; // xDelta yDelta это разницы координат где нажалась мышка и левой верхней координатой карты
                            xDelta = xDel*location0[0]/1852; //2142/1852
                            yDelta = yDel*location1[1]/1080; //1073/1080
                            xBegin=matrix1[0][CardScreenPosition[((MyCardView) view).numb]]*1861/1757;
                            yBegin=matrix1[1][CardScreenPosition[((MyCardView) view).numb]]*681/727;
                            //xBegin = location[0]; // здесь мы запоминаем координату Х, когда мышка нажимает на объект
                            //yBegin = location[1]; // здесь мы запоминаем координату У, когда мышка нажимает на объект
                            isMove = true;
                        }
                        //Toast.makeText(FourPlayActivity.this,  "xBeg="+ xBegin + " yBeg=" + yBegin, Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_UP: // случай, когда кнопка мыши ОТЖАТА
                        newPosition = CARD_AMOUNT + 1;
                        isMove = false;
                        suitable = false; // обозначаем переменную флаг ложью, чтобы далее использовать ее значение (тру или фолз)
                        for (int i = 0; i <= 18; i++) {

                            //Toast.makeText(FourPlayActivity.this,  "x=" + matrix[0][i] + " y=" + matrix[1][i] + " xMouse=" + x + " yMouse=" + y + " i=" + i + " wid=" + cardWidth + " len=" + cardLength, Toast.LENGTH_LONG).show();

                            if ((matrix[0][i] <= x) && (x <= matrix[0][i] + cardWidth) && (matrix[1][i] <= y) && (y <= matrix[1][i]+ cardLength)) {  // условия для попадания координаты мыши/карты в нужный прямоугольник
                                suitable = true; //тут я в сомнениях, ставить тру или фолз, потому что фолз на фолз даст тру или фолз просто поменяется на тру
                                number = i; //запоминаем в переменную позицию на экране, куда перенесли карту
                                //Toast.makeText(FourPlayActivity.this,   "xMouse=" + x + " yMouse=" + y + " i=" + i, Toast.LENGTH_LONG).show();
                                break; //прерываем цикл, хотя возможно в этой строке нет необходимости
                            }
                        }
                        //Toast.makeText(FourPlayActivity.this,   "x=" + matrix[0][number] + " y=" + matrix[1][number] + " xMouse=" + x + " yMouse=" + y + " number=" + number + " wid=" + cardWidth + " len=" + cardLength, Toast.LENGTH_SHORT).show();

                        if (suitable == true) { //Если мышка попала в нужные коррдинаты на экране
                            newPosition=findCardNumber(number);//Находим индекс карты, которая находится на позиции, куда нужно перенести карту
                            suitable=false;
                            if (((number<2)||(number>5))&&(newPosition>CARD_AMOUNT)) {//Если новая позиция карты не в колоде (3,4,5,6 место на экране), а на поле и там еще нет карты
                                suitable = true; //Передвигать можно
                            }
                            if ((suitable==false)&&(number>=2)&&(number<=5)&&(((MyCardView) view).suitNumber==number-2)) {//Если новая позиция карты в колоде (3,4,5,6 место на экране), а не на поле и масть в карте совпадает с позицией в колоде для этой масти
                                suitable = true; //Передвигать можно
                            }
                        }
                        //Toast.makeText(FourPlayActivity.this,   "suitable=" + suitable + " newPos=" + newPosition, Toast.LENGTH_SHORT).show();

                        int oldPosition = CardScreenPosition [((MyCardView) view).numb]; //НОВОЕ запоминаем старую позицию карты пока мы не поменяли массив в цикле for ниже
                        //Toast.makeText(FourPlayActivity.this,   "oldPos " + oldPosition, Toast.LENGTH_SHORT).show();
                        int oldPosRemember = findCardNumber(oldPosition); //НОВОЕ запоминаем индекс карты в массиве IndexInCardArray, которая находиться под картой, которую мы передвинули из стопки масти
                        if (oldPosition == number) { //НОВОЕ если карта после движения оказалась на той же позиции откуда и начала свое движение, то можно сразу переносить ее на начальные коордианты (см. пункт else)
                            suitable = false;
                        }
                        //Toast.makeText(FourPlayActivity.this,   "oldPos=" + oldPosition + " oldPosRem=" + oldPosRemember + " number=" + number, Toast.LENGTH_SHORT).show();
                        if (suitable == true) { //теперь пытаюсь делать переставление объекта после отжатия клавишы, когда флаг тру, но не получается. объект просто двигается по экрану
                            isMove = false;//Изменений в массивах не было
                            if((newPosition < CARD_AMOUNT) && (number >= 2) && (number <= 5) && (((MyCardView) view).suitNumber == number - 2))
                            {//Если новая позиция карты в колоде (3,4,5,6 место на экране), а не на поле и масть в карте совпадает с позицией в колоде для этой масти,  когда в колоде есть карта(ы) такой масти
                                CardArray[IndexInCardArray[newPosition]].setVisibility(View.INVISIBLE);//Предыдущая карта становится невидимой
                                int temp2 = IndexInCardArray[((MyCardView) view).numb];//Запоминаем индекс переносимой карты в массиве CardArray
                                for (int i2 = ((MyCardView) view).numb; i2 < CARD_AMOUNT - 1; i2++) {//Цикл по всем картам, кторые находятся в массиве карт после передвигаемой
                                    CardScreenPosition[i2] = CardScreenPosition[i2+1] ;//Сдвигаем в массиве позиций карт на одно место левее
                                    IndexInCardArray[i2] = IndexInCardArray[i2+1];//Сдвигаем в массиве индексов позиций карт на одно место левее
                                }
                                isMove = true;//Изменения в массиве карт есть
                                IndexInCardArray[CARD_AMOUNT - 1] = temp2;//Вставляем в массив индексов карт на экране позицию передвигаемой карты на последнее место
                                CardScreenPosition[CARD_AMOUNT - 1] = number;//Вставляем в массив позиций карт на экране в последнее место новую позицию передвигаемой карты
                                ((MyCardView) view).numb = CARD_AMOUNT - 1;//В объекте MyCardView сохраняем информацию, что ее индекс в массиве позиций карт и в массиве индексов явлется CARD_AMOUNT-1 (карта стала последней)
                            }
                            //Toast.makeText(FourPlayActivity.this,   "isMove=" + isMove + " numb=" + ((MyCardView) view).numb + " number=" + number, Toast.LENGTH_LONG).show();
                            if(isMove == false)
                            {//Если новая позиция карты может содержать только одну карту или на месте масти, куда переностися карта, нет ни одной карты
                                CardScreenPosition[((MyCardView) view).numb] = number;//Устанавливем для карты новую позицию на экране
                                isMove = true;//зменения в массиве карт есть
                            }
                            //if (oldPosRemember < CARD_AMOUNT) { //НОВОЕ проверяем есть ли вообще под картой, которую мы передвинули еще карты. если есть, делаем видимой
                              //  CardArray[IndexInCardArray[oldPosRemember]].setVisibility(View.VISIBLE); //НОВОЕ делаем карту на старом месте карты, стоявшей в одной из стопки мастей видимой
                            //}

                            //Toast.makeText(FourPlayActivity.this,  "x" + matrix[0][number] + "y" + matrix[1][number], Toast.LENGTH_SHORT).show();

                            Path path = new Path();
                            //path.moveTo(1690f, 650f);
                            path.moveTo(matrix1[0][number]*1861/1757, matrix1[1][number]*681/727);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();

                            view.getLocationOnScreen(location);
                            Toast.makeText(FourPlayActivity.this,  "x" + location[0] + "y" + location[1] + " number=" + number, Toast.LENGTH_SHORT).show();
                        } // ВАЖНО этой скобки в коде не было поэтому посмотреть папин код и понять где скобка

                        //Toast.makeText(FourPlayActivity.this,  "kuku", Toast.LENGTH_SHORT).show();
                        else { //и когда флаг фолз. не работает ни то ни другое
                            Path path = new Path();
                            path.moveTo(xBegin, yBegin);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();
                        }
                        //Toast.makeText(FourPlayActivity.this,  "xWh="+ location[0] + " yWh=" + location[1], Toast.LENGTH_SHORT).show();
                        //Toast.makeText(FourPlayActivity.this,  "x="+ x + " y=" + y, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(FourPlayActivity.this,  "x" + matrix[0][number] + "y" + matrix[1][number], Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_MOVE: //случай, когда объект движется (а движется он только тогда, когда кнопка мыши НАЖАТА и не была ОТЖАТА с прошлого раза)

                        if (view.getVisibility() == View.VISIBLE ) {
                            Path path = new Path();
                            path.moveTo(x+xDelta, y+yDelta);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();

                          //Toast.makeText(FourPlayActivity.this,  "x1=" + x1 + " y1=" + y1, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(FourPlayActivity.this,   "x1" + x, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

     /*
     ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                    .getLayoutParams();
                            layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = y - yDelta;
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            view.setLayoutParams(layoutParams);

                            xDelta = (int) view.getX() - x; //внутренние переменные: xDelta YDelta это не переменные моего объекта, а переменные в программе
                            yDelta = (int) view.getY() - y; // xDelta yDelta это разницы координат где нажалась мышка и левой верхней координатой карты
                            xBegin = (int) view.getX(); // здесь мы запоминаем координату Х, когда мышка нажимает на объект
                            yBegin = (int) view.getY(); // здесь мы запоминаем координату У, когда мышка нажимает на объект
                            isMove = true;
                             */

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