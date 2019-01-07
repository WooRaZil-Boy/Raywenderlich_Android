object X { //싱글
    var x = 0
}

//Tools ▸ Kotlin ▸ Show Kotlin Bytecode 를 선택하면, Kotlin Bytecode를 살펴볼 수 있다.
//Kotlin Bytecode 창에서 Decompile 버튼을 누르면 디컴파일된 Java가 새 편집 창에 열린다.
//Java로 작성된 코드를 확인해 보면, Kotlin object로 작성한 코드가 싱글톤임을 알 수 있다.
//코드를 비교해 보면 Kotlin에서는 매우 적은 코드로 싱글톤 패턴을 구현할 수 있음을 알 수 있다.