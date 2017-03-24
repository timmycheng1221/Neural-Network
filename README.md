# Perception-Machine

## 程式執行說明：
- 一開始的畫面  
  ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/1.jpg)
- 按下右上角的「Open File」→匯入資料集→「Input」方框顯示資料集
  ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/2.jpg)
- 輸入學習率、訓練準確率和迭代次數，並按下右下角的「Train」 
  ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/3.jpg)  
→顯示訓練結果(圖形、左下角的「Training Accuracy」顯示訓練準確率、「Weight Vector」顯示訓練後的鍵結值、「Output」方框顯示訓練過程中鍵結值的變化)
 
 
- 按下右下角的「Test」，顯示測試結果(圖形、左下角的「Testing Accuracy」顯示測試準確率)
  ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/4.jpg)
## 程式簡介：
- Main.java：  
建立主要的GUI介面，先將讀入的資料夾依照期望值做排序分成兩類，並將期望值正規化成(-1, 1)方便感知機做處理，若資料集的數量少於10筆，則不會將資料集分成訓練和測試兩種，全部資料集將都會進行訓練。感知機訓練前先隨機初始化鍵結值(w[0], w[1], w[2])，範圍為[0, 1]，閥值為-1，在迭代次數內或未達到訓練準確率時，用資料集和鍵結值計算出一個值，若這個值 > 0(or < 0)，但期望值 < 0(or > 0)，則調整鍵結值，反之，鍵結值不變。   
訓練完成後將資料集和鍵結值傳到Plot.java進行繪圖工作
- Plot.java：  
繪製資料集(訓練+測試)的點和感知機訓練後的線  
- 實驗結果(兩類、二維、簡單感知機)  
  - 2Ccircle1.txt(訓練) 					
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/5.jpg)
  - 2Ccircle1.txt(測試)
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/6.jpg)
  - 2CloseS2.txt(訓練)
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/7.jpg)
  - 2CloseS2.txt(測試)
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/8.jpg)
  - 2CS.txt(訓練)
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/9.jpg)
  - 2CS.txt(測試)
    ![GUI](https://raw.githubusercontent.com/timmycheng1221/Perception-Machine/master/image/10.jpg)
 
## 實驗結果分析及討論。<含鍵結值、訓練次數、學習率、訓練正確率、測試正確率等等討論>  
- 第一個實驗結果：  
由於只是簡單的感知機，故無法以一條線做分兩類的工作  
訓練正確率和測試正確率等於A類數量/總資料集  
- 第二個實驗結果：  
目測是可以正常分類的資料集，程式也如預期的達到訓練正確率，但沒想到才迭代沒幾次就可以達到訓練正確率100%，可能是一開始隨機的鏈結值很接近理想的鍵結值  
- 第三個實驗結果：  
目測是可以正常分類的資料集，但是不管訓練率或迭代次數怎麼改都不太能達到良好的訓練正確率，也重新審視過程式使用的公式，但還是找不到原因是什麼
