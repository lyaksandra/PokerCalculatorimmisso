package com.example.pokercalculatorimmisso;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokercalculatorimmisso.adapter.CardAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class FourPlayActivity extends Activity {

    ObjectAnimator animation;
    MyCardView aceClubs;

    CardAdapter adapter;
    BottomSheetDialog dialog;
    private final int dpiPixel5 = 440; // dpi моего эмулятора

    private ViewGroup mainLayout;

    private final int CARD_AMOUNT = 52; //общее количество карт
    MyCardView[] CardArray = new MyCardView [CARD_AMOUNT];//Объекты визуальные карт, номер визуальной карты в этом массиве соотвествует значениею элемента массива в IndexInCardArray
    int[] CardScreenPosition = new int [CARD_AMOUNT];//Позиция каждой карты на экране (плюс в каждой позиции на экране, выводится та карта, номер которой в этом массиве больше)
    int[] IndexInCardArray = new int [CARD_AMOUNT];//Номер в массиве - это индекс визуальной карты в массиве CardArray, один и тот же индекс в массивах CardScreenPosition и IndexInCardArray соответствуют одной визуальной карте
    int[] SuitArray = new int [CARD_AMOUNT]; //массив, который запоминает позиции карт одной масти в массиве IndexInCardArray
    private int oldPosition, newPosition; //запоминать позиции карт до/после переноса

    private boolean isFirst = true; //переменная флаг, чтобы определять массив в онТаче только единожды
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
            //массивы которые буду использоваться в экшнаппе для забора координат для массива
    public int[] location = new int[2];
    int[] location0 = new int[2];
    int[] location1 = new int[2];

    //объявление переменных малевичей
    ImageView black0, black1, black2, black3, black4, black5, black6, black7, black8, black9, black10, black11, black12, black13, black14, black15, black16, black17, black18;
    ImageView white0, white1;
    TextView countResultFirst, countResultSecond;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // начался жизненный цикл онКриэйт
        super.onCreate(savedInstanceState); // наследование функций и методов онКриэйта
        setContentView(R.layout.activity_four_play); //привязка к XML-файла к активити
        mainLayout = findViewById(R.id.four_play); // вводим новый объект, которая будет отвечать за XML файл массива
        countResultFirst = findViewById(R.id.count_result_first);
        countResultSecond = findViewById(R.id.count_result_second);
        CardArray[0]=findViewById(R.id.two_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[1]=findViewById(R.id.two_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[2]=findViewById(R.id.two_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[3]=findViewById(R.id.two_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[4]=findViewById(R.id.three_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[5]=findViewById(R.id.three_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[6]=findViewById(R.id.three_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[7]=findViewById(R.id.three_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[8]=findViewById(R.id.four_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[9]=findViewById(R.id.four_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[10]=findViewById(R.id.four_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[11]=findViewById(R.id.four_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[12]=findViewById(R.id.five_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[13]=findViewById(R.id.five_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[14]=findViewById(R.id.five_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[15]=findViewById(R.id.five_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[16]=findViewById(R.id.six_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[17]=findViewById(R.id.six_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[18]=findViewById(R.id.six_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[19]=findViewById(R.id.six_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[20]=findViewById(R.id.seven_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[21]=findViewById(R.id.seven_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[22]=findViewById(R.id.seven_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[23]=findViewById(R.id.seven_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[24]=findViewById(R.id.eight_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[25]=findViewById(R.id.eight_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[26]=findViewById(R.id.eight_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[27]=findViewById(R.id.eight_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[28]=findViewById(R.id.nine_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[29]=findViewById(R.id.nine_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[30]=findViewById(R.id.nine_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[31]=findViewById(R.id.nine_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[32]=findViewById(R.id.ten_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[33]=findViewById(R.id.ten_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[34]=findViewById(R.id.ten_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[35]=findViewById(R.id.ten_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[36]=findViewById(R.id.jack_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[37]=findViewById(R.id.jack_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[38]=findViewById(R.id.jack_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[39]=findViewById(R.id.jack_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[40]=findViewById(R.id.queen_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[41]=findViewById(R.id.queen_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[42]=findViewById(R.id.queen_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[43]=findViewById(R.id.queen_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[44]=findViewById(R.id.king_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[45]=findViewById(R.id.king_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[46]=findViewById(R.id.king_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[47]=findViewById(R.id.king_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[48]=findViewById(R.id.ace_diamonds); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[49]=findViewById(R.id.ace_clubs); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[50]=findViewById(R.id.ace_spades); // объект типа MyCardView, который привязан по ID с объектом из XML-файла
        CardArray[51]=findViewById(R.id.ace_hearts); // объект типа MyCardView, который привязан по ID с объектом из XML-файла

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

        for (int index = 0; index < CARD_AMOUNT; index++) {//Цикл по всем картам на экране
         CardArray[index].setOnTouchListener(onTouchListener()); //привязываем к объекту метод, который ждет, пока на объект не нажмут
          CardArray[index].numb=index;//Устанавливаем внутри объекта карты позицию в массивах IndexInCardArray и CardScreenPosition
          IndexInCardArray[index] = index;
      }

        CardScreenPosition[0]=2;//Diamonds на 3 месте
        CardScreenPosition[4]=2;//Diamonds на 3 месте
        CardScreenPosition[8]=2;//Diamonds на 3 месте
        CardScreenPosition[12]=2;//Diamonds на 3 месте
        CardScreenPosition[16]=2;//Diamonds на 3 месте
        CardScreenPosition[20]=2;//Diamonds на 3 месте
        CardScreenPosition[24]=2;//Diamonds на 3 месте
        CardScreenPosition[28]=2;//Diamonds на 3 месте
        CardScreenPosition[32]=2;//Diamonds на 3 месте
        CardScreenPosition[36]=2;//Diamonds на 3 месте
        CardScreenPosition[40]=2;//Diamonds на 3 месте
        CardScreenPosition[44]=2;//Diamonds на 3 месте
        CardScreenPosition[48]=2;//Diamonds на 3 месте

        CardArray[0].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[4].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[8].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[12].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[16].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[20].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[24].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[28].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[32].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[36].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[40].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[44].suitNumber=0;//Устанавливаем внутри объекта карты масть
        CardArray[48].suitNumber=0;//Устанавливаем внутри объекта карты масть

        CardScreenPosition[1]=3;//Clubs на 4 месте
        CardScreenPosition[5]=3;//Clubs на 4 месте
        CardScreenPosition[9]=3;//Clubs на 4 месте
        CardScreenPosition[13]=3;//Clubs на 4 месте
        CardScreenPosition[17]=3;//Clubs на 4 месте
        CardScreenPosition[21]=3;//Clubs на 4 месте
        CardScreenPosition[25]=3;//Clubs на 4 месте
        CardScreenPosition[29]=3;//Clubs на 4 месте
        CardScreenPosition[33]=3;//Clubs на 4 месте
        CardScreenPosition[37]=3;//Clubs на 4 месте
        CardScreenPosition[41]=3;//Clubs на 4 месте
        CardScreenPosition[45]=3;//Clubs на 4 месте
        CardScreenPosition[49]=3;//Clubs на 4 месте

        CardArray[1].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[5].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[9].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[13].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[17].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[21].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[25].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[29].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[33].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[37].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[41].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[45].suitNumber=1;//Устанавливаем внутри объекта карты масть
        CardArray[49].suitNumber=1;//Устанавливаем внутри объекта карты масть

        CardScreenPosition[2]=4;//Spades на 5 месте
        CardScreenPosition[6]=4;//Spades на 5 месте
        CardScreenPosition[10]=4;//Spades на 5 месте
        CardScreenPosition[14]=4;//Spades на 5 месте
        CardScreenPosition[18]=4;//Spades на 5 месте
        CardScreenPosition[22]=4;//Spades на 5 месте
        CardScreenPosition[26]=4;//Spades на 5 месте
        CardScreenPosition[30]=4;//Spades на 5 месте
        CardScreenPosition[34]=4;//Spades на 5 месте
        CardScreenPosition[38]=4;//Spades на 5 месте
        CardScreenPosition[42]=4;//Spades на 5 месте
        CardScreenPosition[46]=4;//Spades на 5 месте
        CardScreenPosition[50]=4;//Spades на 5 месте

        CardArray[2].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[6].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[10].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[14].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[18].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[22].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[26].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[30].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[34].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[38].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[42].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[46].suitNumber=2;//Устанавливаем внутри объекта карты масть
        CardArray[50].suitNumber=2;//Устанавливаем внутри объекта карты масть

        CardScreenPosition[3]=5;//Spades на 6 месте
        CardScreenPosition[7]=5;//Spades на 6 месте
        CardScreenPosition[11]=5;//Spades на 6 месте
        CardScreenPosition[15]=5;//Spades на 6 месте
        CardScreenPosition[19]=5;//Spades на 6 месте
        CardScreenPosition[23]=5;//Spades на 6 месте
        CardScreenPosition[27]=5;//Spades на 6 месте
        CardScreenPosition[31]=5;//Spades на 6 месте
        CardScreenPosition[35]=5;//Spades на 6 месте
        CardScreenPosition[39]=5;//Spades на 6 месте
        CardScreenPosition[43]=5;//Spades на 6 месте
        CardScreenPosition[47]=5;//Spades на 6 месте
        CardScreenPosition[51]=5;//Spades на 6 месте

        CardArray[3].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[7].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[11].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[15].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[19].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[23].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[27].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[31].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[35].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[39].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[43].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[47].suitNumber=3;//Устанавливаем внутри объекта карты масть
        CardArray[51].suitNumber=3;//Устанавливаем внутри объекта карты масть

        CardArray[0].nomination = 2;
        CardArray[1].nomination = 2;
        CardArray[2].nomination = 2;
        CardArray[3].nomination = 2;
        CardArray[4].nomination = 3;
        CardArray[5].nomination = 3;
        CardArray[6].nomination = 3;
        CardArray[7].nomination = 3;
        CardArray[8].nomination = 4;
        CardArray[9].nomination = 4;
        CardArray[10].nomination = 4;
        CardArray[11].nomination = 4;
        CardArray[12].nomination = 5;
        CardArray[13].nomination = 5;
        CardArray[14].nomination = 5;
        CardArray[15].nomination = 5;
        CardArray[16].nomination = 6;
        CardArray[17].nomination = 6;
        CardArray[18].nomination = 6;
        CardArray[19].nomination = 6;
        CardArray[20].nomination = 7;
        CardArray[21].nomination = 7;
        CardArray[22].nomination = 7;
        CardArray[23].nomination = 7;
        CardArray[24].nomination = 8;
        CardArray[25].nomination = 8;
        CardArray[26].nomination = 8;
        CardArray[27].nomination = 8;
        CardArray[28].nomination = 9;
        CardArray[29].nomination = 9;
        CardArray[30].nomination = 9;
        CardArray[31].nomination = 9;
        CardArray[32].nomination = 10;
        CardArray[33].nomination = 10;
        CardArray[34].nomination = 10;
        CardArray[35].nomination = 10;
        CardArray[36].nomination = 11;//J
        CardArray[37].nomination = 11;//J
        CardArray[38].nomination = 11;//J
        CardArray[39].nomination = 11;//J
        CardArray[40].nomination = 12;//Q
        CardArray[41].nomination = 12;//Q
        CardArray[42].nomination = 12;//Q
        CardArray[43].nomination = 12;//Q
        CardArray[44].nomination = 13;//K
        CardArray[45].nomination = 13;//K
        CardArray[46].nomination = 13;//K
        CardArray[47].nomination = 13;//K
        CardArray[48].nomination = 14;//A
        CardArray[49].nomination = 14;//A
        CardArray[50].nomination = 14;//A
        CardArray[51].nomination = 14;//A

        Button countButton;
        countButton = findViewById(R.id.button_count);
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index0 = findCardNumber(0);
                int index1 = findCardNumber(1);
                int index8 = findCardNumber(8);
                int index9 = findCardNumber(9);
                int index10 = findCardNumber(10);
                int index11 = findCardNumber(11);
                int index12 = findCardNumber(12);
                if ((index0<CARD_AMOUNT+1) && (index1<CARD_AMOUNT+1) && (index8 == CARD_AMOUNT+1) && (index9 == CARD_AMOUNT+1) && (index10 == CARD_AMOUNT+1) && (index11 == CARD_AMOUNT+1) && (index12 == CARD_AMOUNT+1)){
                    if (CardArray[index0].nomination == CardArray[index1].nomination) {
                        countResultFirst.setText("Вірогідність отримати: сет - 10.5%");
                        countResultSecond.setText("фулл-хауз - 0.73%, " + "каре - 0.24%");
                    } else if (CardArray[(findCardNumber(0))].suitNumber == CardArray[(findCardNumber(1))].suitNumber) {
                        countResultFirst.setText("Вірогідність отримати: флеш - 0.842%");
                        countResultSecond.setText("флеш-дро - 10.9%");
                    } else {
                        countResultFirst.setText("Вірогідність отримати: пара - 32.4%");
                        countResultSecond.setText("дві пари - 2%");
                    }
                } else {
                    if ((index0==CARD_AMOUNT+1) || (index1==CARD_AMOUNT+1)) {
                        countResultFirst.setText("Замало карт для розрахунку");
                    }
                    if ((index8 < CARD_AMOUNT+1) || (index9 < CARD_AMOUNT+1) || (index10 < CARD_AMOUNT+1) || (index11 < CARD_AMOUNT+1) || (index12 < CARD_AMOUNT+1)){
                        countResultFirst.setText("Буде в наступному оновленні");
                    }
                }
            }
        });
    }


    @Override
    protected void onStart() { // жизненный цикл онСтарт
        super.onStart(); // наследование функций и методов онСтарта
    }

    protected int findSuit (int Position) { //функция, которая возвращает кол-во карт, находящихся в колоде одной масти и заполняющая массив с помощью индексов из IndexInCardArray
        int inSuit = 0;
        for (int i = 0; i < CARD_AMOUNT; i++) {
            SuitArray[i] = CARD_AMOUNT + 1; //заполняет пустой массив одинаковыми числами
        }
        for (int j = 0; j < CARD_AMOUNT; j++) {
            if (CardScreenPosition[j] == Position) {
                SuitArray[inSuit] = j; //заполняет массив нужными индексами
                inSuit = inSuit + 1;
            }
        }
        return inSuit;
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

                if (isFirst == true) { //заполняем массив координат
                    black0.getLocationOnScreen(location);
                    matrix[0][0] = location[0];
                    matrix[1][0] = location[1];
                    black1.getLocationOnScreen(location);
                    matrix[0][1] = location[0];
                    matrix[1][1] = location[1];
                    black2.getLocationOnScreen(location);
                    matrix[0][2] = location[0];
                    matrix[1][2] = location[1];
                    black3.getLocationOnScreen(location);
                    matrix[0][3] = location[0];
                    matrix[1][3] = location[1];
                    black4.getLocationOnScreen(location);
                    matrix[0][4] = location[0];
                    matrix[1][4] = location[1];
                    black5.getLocationOnScreen(location);
                    matrix[0][5] = location[0];
                    matrix[1][5] = location[1];
                    black6.getLocationOnScreen(location);
                    matrix[0][6] = location[0];
                    matrix[1][6] = location[1];
                    black7.getLocationOnScreen(location);
                    matrix[0][7] = location[0];
                    matrix[1][7] = location[1];
                    black8.getLocationOnScreen(location);
                    matrix[0][8] = location[0];
                    matrix[1][8] = location[1];
                    black9.getLocationOnScreen(location);
                    matrix[0][9] = location[0];
                    matrix[1][9] = location[1];
                    black10.getLocationOnScreen(location);
                    matrix[0][10] = location[0];
                    matrix[1][10] = location[1];
                    black11.getLocationOnScreen(location);
                    matrix[0][11] = location[0];
                    matrix[1][11] = location[1];
                    black12.getLocationOnScreen(location);
                    matrix[0][12] = location[0];
                    matrix[1][12] = location[1];
                    black13.getLocationOnScreen(location);
                    matrix[0][13] = location[0];
                    matrix[1][13] = location[1];
                    black14.getLocationOnScreen(location);
                    matrix[0][14] = location[0];
                    matrix[1][14] = location[1];
                    black15.getLocationOnScreen(location);
                    matrix[0][15] = location[0];
                    matrix[1][15] = location[1];
                    black16.getLocationOnScreen(location);
                    matrix[0][16] = location[0];
                    matrix[1][16] = location[1];
                    black17.getLocationOnScreen(location);
                    matrix[0][17] = location[0];
                    matrix[1][17] = location[1];
                    black18.getLocationOnScreen(location);
                    matrix[0][18] = location[0];
                    matrix[1][18] = location[1];

                    isFirst = false;
                }

                final int x = (int) event.getRawX(); //координата х где нажалась кнопка мыши
                final int y = (int) event.getRawY(); //координата у где нажалась кнопка мыши

                switch (event.getAction() & MotionEvent.ACTION_MASK) { // рассматриваем три случая, которые необходимы для передвижения объекта (кнопка нажата -- движение -- кнопка отжата)

                    case MotionEvent.ACTION_DOWN:// случай, когда кнопка мыши НАЖАТА

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
                            isMove = true;
                        }
                        break;

                    case MotionEvent.ACTION_UP: // случай, когда кнопка мыши ОТЖАТА
                        newPosition = CARD_AMOUNT + 1;
                        isMove = false;
                        suitable = false; // обозначаем переменную флаг ложью, чтобы далее использовать ее значение (тру или фолз)
                        for (int i = 0; i <= 18; i++) {

                            if ((matrix[0][i] <= x) && (x <= matrix[0][i] + cardWidth) && (matrix[1][i] <= y) && (y <= matrix[1][i]+ cardLength)) {  // условия для попадания координаты мыши/карты в нужный прямоугольник
                                suitable = true; //тут я в сомнениях, ставить тру или фолз, потому что фолз на фолз даст тру или фолз просто поменяется на тру
                                number = i; //запоминаем в переменную позицию на экране, куда перенесли карту
                                break; //прерываем цикл, хотя возможно в этой строке нет необходимости
                            }
                        }

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

                        oldPosition = CardScreenPosition [((MyCardView) view).numb]; //НОВОЕ запоминаем старую позицию карты пока мы не поменяли массив в цикле for ниже
                        if (oldPosition == number) { //НОВОЕ если карта после движения оказалась на той же позиции откуда и начала свое движение, то можно сразу переносить ее на начальные коордианты (см. пункт else)
                            suitable = false;
                            //начало алгоритма
                            if ((number >=2) && (number <=5)){ //алгоритм работает только с местами, касающихся мастей
                                int suitResult = findSuit(number); //фиксируем переменную, которую нам выдает функция по месту
                                switch (suitResult) { //рассматриваем случаи когда
                                    case 0: //переменная равна 0
                                        Toast.makeText(FourPlayActivity.this,   "ОШИБКА", Toast.LENGTH_SHORT).show(); //ошибка, потому что как на пустом месте может что-то быть
                                        break;
                                    case 1: //переменная равна 1
                                        break; //ничего не делаем, ведь механизм переноса на прежнее место прописан в else
                                    default: //переменная 2 и больше
                                        int temp3 = IndexInCardArray[SuitArray[suitResult-1]];//Запоминаем индекс нулевой карты в массиве SuitArray
                                        int temp4 = CardScreenPosition[SuitArray[suitResult-1]];
                                        CardArray[IndexInCardArray[SuitArray[suitResult-2]]].setVisibility(View.VISIBLE);
                                        for (int i = suitResult-1; i>0; i--) {
                                                CardArray[IndexInCardArray[SuitArray[i-1]]].numb = SuitArray[i];
                                                CardScreenPosition[SuitArray[i]] = CardScreenPosition[SuitArray[i-1]] ;//Сдвигаем в массиве позиций карт на одно место левее
                                                IndexInCardArray[SuitArray[i]] = IndexInCardArray[SuitArray[i-1]];//Сдвигаем в массиве индексов позиций карт на одно место левее
                                        }
                                        IndexInCardArray[SuitArray[0]] = temp3;//Вставляем в массив индексов карт на экране позицию передвигаемой карты на последнее место
                                        CardScreenPosition[SuitArray[0]] = temp4;//Вставляем в массив позиций карт на экране в последнее место новую позицию передвигаемой карты
                                        CardArray[temp3].numb = SuitArray[0];//В объекте MyCardView сохраняем информацию, что ее индекс в массиве позиций карт и в массиве индексов явлется CARD_AMOUNT-1 (карта стала последней)
                                        view.setVisibility(View.INVISIBLE);//CardArray[temp3].setVisibility(View.INVISIBLE);

                                        break;
                                }
                            }
                            //конец алгоритма
                        }
                        if (suitable == true) { //теперь пытаюсь делать переставление объекта после отжатия клавишы, когда флаг тру, но не получается. объект просто двигается по экрану
                            isMove = false;//Изменений в массивах не было
                            if((newPosition < CARD_AMOUNT) && (number >= 2) && (number <= 5) && (((MyCardView) view).suitNumber == number - 2))
                            {//Если новая позиция карты в колоде (3,4,5,6 место на экране), а не на поле и масть в карте совпадает с позицией в колоде для этой масти,  когда в колоде есть карта(ы) такой масти
                                CardArray[IndexInCardArray[newPosition]].setVisibility(View.INVISIBLE);//Предыдущая карта становится невидимой
                                int temp2 = IndexInCardArray[((MyCardView) view).numb];//Запоминаем индекс переносимой карты в массиве CardArray
                                for (int i2 = ((MyCardView) view).numb; i2 < CARD_AMOUNT - 1; i2++) {//Цикл по всем картам, кторые находятся в массиве карт после передвигаемой
                                    CardArray[IndexInCardArray[i2+1]].numb = i2;
                                    CardScreenPosition[i2] = CardScreenPosition[i2+1] ;//Сдвигаем в массиве позиций карт на одно место левее
                                    IndexInCardArray[i2] = IndexInCardArray[i2+1];//Сдвигаем в массиве индексов позиций карт на одно место левее
                                }
                                isMove = true;//Изменения в массиве карт есть
                                IndexInCardArray[CARD_AMOUNT - 1] = temp2;//Вставляем в массив индексов карт на экране позицию передвигаемой карты на последнее место
                                CardScreenPosition[CARD_AMOUNT - 1] = number;//Вставляем в массив позиций карт на экране в последнее место новую позицию передвигаемой карты
                                ((MyCardView) view).numb = CARD_AMOUNT - 1;//В объекте MyCardView сохраняем информацию, что ее индекс в массиве позиций карт и в массиве индексов явлется CARD_AMOUNT-1 (карта стала последней)
                            }
                            if(isMove == false)
                            {//Если новая позиция карты может содержать только одну карту или на месте масти, куда переностися карта, нет ни одной карты
                                CardScreenPosition[((MyCardView) view).numb] = number;//Устанавливем для карты новую позицию на экране
                                isMove = true;//зменения в массиве карт есть
                            }
                            int oldPosRemember = findCardNumber(oldPosition); //НОВОЕ запоминаем индекс карты в массиве IndexInCardArray, которая находиться под картой, которую мы передвинули из стопки масти
                            if (oldPosRemember < CARD_AMOUNT) { //НОВОЕ проверяем есть ли вообще под картой, которую мы передвинули еще карты. если есть, делаем видимой
                                CardArray[IndexInCardArray[oldPosRemember]].setVisibility(View.VISIBLE); //НОВОЕ делаем карту на старом месте карты, стоявшей в одной из стопки мастей видимой
                            }

                            Path path = new Path();
                            path.moveTo(matrix1[0][number]*1861/1757, matrix1[1][number]*681/727);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();

                            view.getLocationOnScreen(location);
                        }
                        else { //и когда флаг фолз. не работает ни то ни другое
                            Path path = new Path();
                            path.moveTo(xBegin, yBegin);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();
                        }
                        isMove=false;
                        break;

                    case MotionEvent.ACTION_MOVE: //случай, когда объект движется (а движется он только тогда, когда кнопка мыши НАЖАТА и не была ОТЖАТА с прошлого раза)

                        if (view.getVisibility() == View.VISIBLE ) {
                            Path path = new Path();
                            path.moveTo(x+xDelta, y+yDelta);

                            animation = ObjectAnimator.ofFloat(view, View.X, View.Y, path); // View.X, View.Y
                            animation.start();
                        }
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    //Toast.makeText(FourPlayActivity.this,   "x1" + x, Toast.LENGTH_SHORT).show();

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