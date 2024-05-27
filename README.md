[ 상품 ]
상품 entity/repository 만들기

[ 쿠폰 ]
쿠폰 entity/repository 만들기 (회원 id랑 연관관계)
쿠폰 enum 만들기 : 정액제, 정률제

[ 포인트 ]
포인트 entity/repository 만들기 (회원 id랑 연관관계)

[ 결제 ]
결제 entity / repository 만들기

[ 코드 흐름 ]
1. request 받기 (주문 정보, 결제 금액)
2. 클라이언트에서 보낸 정보가 request와 맞는지 확인
3. 쿠폰 / 포인트 필요시 사용 : 포인트가 없는 경우, 쿠폰/포인트 어떤것을 먼저 사용하는지, 적용 금액이 상품 금액을 넘어가는 등 다양한 케이스 다루기
4. 결제가 승인 되도록 결제 대기 상태로 만들기
5. toss 결제 승인 api 호출
6. api 호출 결과와 결재 상태를 대조해서 처리하기
-> 하나의 service안에 다양한 내용이 들어가야 하므로 메서드로 따로 빼놓는것도 괜찮을듯.