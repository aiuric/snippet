// Arrow Function (js와 비슷)
// function body가 한 줄 이면 {return 값;} 생략가능
int timesFour(int x) => timesTwo(timesTwo(x));

// 일반 함수 형식으로 정의하면
int timesFour(int x) {
  return timesTwo(timesTwo(x));
}

// Functions parameter로 전달 가능
// Function은 Object이다
int runTwice(int x, Function f) {
  for (var i = 0; i < 2; i++) {
    x = f(x);
  }
  return x;
}

// parameter
// In Dart with my understanding, method parameter can be given in two type.

// Required parameter
// Optional parameter (positional, named & default)
// < Required Parameter >

// Required parameter is a well know old style parameter which we all familiar with it

// example:
findVolume(int length, int breath, int height) {
  print('length = $length, breath = $breath, height = $height');
}

findVolume(10,20,30);
// output:
// length = 10, breath = 20, height = 30
  
  
// < Optional Positional Parameter >

// parameter will be disclosed with square bracket [ ] & square bracketed parameter are optional.

// example:

findVolume(int length, int breath, [int height]) {
 print('length = $length, breath = $breath, height = $height');
}

findVolume(10,20,30);//valid
findVolume(10,20);//also valid
// output:
// length = 10, breath = 20, height = 30
// length = 10, breath = 20, height = null // no value passed so height is null

// < Optional Named Parameter >

// named이므로 순서도 상관없음
// parameter will be disclosed with curly bracket { }
// curly bracketed parameter are optional.
// have to use parameter name to assign a value which separated with colan :
// in curly bracketed parameter order does not matter
// these type parameter help us to avoid confusion while passing value for a function which has many parameter.
// example:

findVolume(int length, int breath, {int height}) {
 print('length = $length, breath = $breath, height = $height');
}

findVolume(10,20,height:30);//valid & we can see the parameter name is mentioned here.
findVolume(10,20);//also valid
// output:
// length = 10, breath = 20, height = 30
// length = 10, breath = 20, height = null

// < Optional Default Parameter >

// same like optional named parameter in addition we can assign default value for this parameter.
// which means no value is passed this default value will be taken.
// example:

findVolume(int length, int breath, {int height=10}) {
 print('length = $length, breath = $breath, height = $height');
} 

findVolume(10,20,height:30);//valid
findVolume(10,20);//valid 

// output:
// length = 10, breath = 20, height = 30
// length = 10, breath = 20, height = 10 // default value 10 is taken

