// Arrow Function (js와 비슷)
// function body가 한 줄 이면 {return 값;} 생략가능
int timesFour(int x) => timesTwo(timesTwo(x));

// 일반 함수 형식으로 정의하면
int timesFour(int x) {
  return timesTwo(timesTwo(x));
}
