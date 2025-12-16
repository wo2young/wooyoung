# Dockerfile

# python으로 프로그램을 실행하고 마치고자 할 경우
# B Part
FROM python:3

# C Part
# python 컨테이너에서 사용되는 경로
# 실제로 본 이미지를 실행해보면 /usr/src/app 경로에서 python이 실행되는 것을 확인 가능함
WORKDIR /usr/src/app

# 현재 local 경로에 있는 작업 폴더(예: prac11) 하부를 컨테이너 내부로 copy함
# D Part
COPY . .

# 아래 명령어를 실행하고 컨테이너는 종료되어 컨테이너에 접속이 불가함 
# python ./main.py가 실행됨. 
# 이때, main.py는 본인의 python 폴더와 파일 이름으로 변경 가능
# E Part
CMD [ "python3", "./main.py" ]