# 이미지 검색 앱 만들기

(시연 영상은 용량 이슈로 없습니다.)

- 메인 화면

<img src ="https://github.com/user-attachments/assets/825104fd-d819-470b-9b43-7eaf87bf771c" width="400" height="700"/>

    -> 검색창에 키워드를 입력하고 검색 버튼 누르면 검색 결과들이 나타나도록 구현
    
    -> 페이지는 총 2개로 구성되어 있으며, 검색 결과를 보여주는 화면과 즐겨찾기 화면으로 구성

- 검색 결과 화면
<img src ="https://github.com/user-attachments/assets/f7373301-9921-4485-b8a7-880a1e398aaf" width="400" height="700"/>

    -> "르세라핌" 같은 키워드를 입력하고 검색 버튼을 누르면 키워드에 맞는 검색 결과를 보여주는 화면
  
    -> Floating Button을 누르면 검색 결과가 나온 화면에서 가장 위쪽에 위치한 결과로 자동 이동
<img src ="https://github.com/user-attachments/assets/242f8d64-efe6-42f7-a18f-2a53d6633046" width="400" height="700"/>

    -> 이미지와 비디오를 섞어서 보는 기능도 추가
  
    -> 구분을 위해 [Image]와 [Video]로 나눠서 표시

- 즐겨찾기 기능 (좋아요 기능이라고 부를 수도 있긴 한데..)
<img src ="https://github.com/user-attachments/assets/35832606-cbcc-4d22-92ef-b355a1494c61" width="400" height="700"/>

    -> 이미지를 클릭하면 하트가 뜨고, 다시 한 번 클릭하면 하트가 사라지는 기능 구현
  
    -> 하트가 뜨며 즐겨찾기에 등록한 이미지(이미지 파일이면 이미지, 동영상 파일이면 썸네일을 뜻함)가 보관함으로 이동
  
    -> 보관함 페이지에 순서대로 저장됨

- 보관함 화면 (즐겨찾기 보관함)
<img src ="https://github.com/user-attachments/assets/ec87b375-b351-4a30-8eeb-602d3725662b" width="400" height="700"/>

    -> 검색 결과 화면에서 즐겨찾기 등록하면 보관함에 등록 가능
  
    -> 검색 결과 화면에서 한 번 더 누르면 (즐겨찾기 취소하면) 보관함에서 삭제
  
    -> 보관함에서도 눌러서 삭제가 되긴 하는데, 그러면 검색 결과 화면에서 반영이 안 되는 문제가 있는데
  
    -> 보관함에서 검색 결과 화면으로 적용되는 기능은 추후 구현 예정
