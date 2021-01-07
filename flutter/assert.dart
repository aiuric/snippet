class PlaceholderContent extends StatelessWidget {
  // constructor with named parameters
  const PlaceholderContent({
    this.title,
    this.message,
  });
  final String title;
  final String message;
  
  // TODO: Implement build method
}

// assert를 사용해서 객체 생성자에서의 널처리방법
// 지금은 null-safety적용되서 이럴필요는 없음
const PlaceholderContent({
  this.title = 'Nothing here',
  this.message = 'Add a new item to get started',
}) : assert(title != null && message != null);
