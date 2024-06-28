SELECT
            BOARD_NO,
            BOARD_TITLE,
            BOARD_WRITER,
            COUNT,
            CREATE_DATE,
            ORIGIN_NAME,
            RNUM
      FROM
            (
            SELECT
                    BOARD_NO,
                    BOARD_TITLE,
                    BOARD_WRITER,
                    COUNT,
                    CREATE_DATE,
                    ORIGIN_NAME,
                    ROWNUM RNUM
              FROM
                    (SELECT
                            BOARD_NO,
                            BOARD_TITLE,
                            BOARD_WRITER,
                            COUNT,
                            CREATE_DATE,
                            ORIGIN_NAME
                      FROM
                            BOARD
                     WHERE
                            STATUS = 'Y'
                     ORDER
                        BY
                            BOARD_NO DESC)
            )
      WHERE 
             RNUM BETWEEN 11 AND 20;
             
--개수 세기
SELECT
        COUNT,
  FROM
        BOARD
 WHERE
        STATUS = 'Y'
        
        AND
        --사용자가 WRITER를 조건으로 "U"라는 키워드를 검색했을 때 결과 == 10
        BOARD_WRITER LIKE '%' || 'U' || '%';

-- 조건에 부합하는 게시글의 행의 수 알아내기
-- 조건 / 키워드
-- 작성자 / 내용/ 제목

-- 키워드
-- like '%%'
SELECT
        COUNT,
  FROM
        BOARD
 WHERE
        STATUS = 'Y'
        
        AND
            조건 컬럼
            LIKE '%' || '검색값' || '%'

alter table board modify change_name varchar2(150);
commit;

 SELECT
	          CHANGE_NAME,
	          BOARD_TITLE,
	          USER_NAME,
	          BOARD_CONTENT,
	          CHANGE_NAME
	  	FROM
	          BOARD, MEMBER
	   WHERE
	          BOARD.BOARD_WRITER = MEMBER.USER_ID
	    AND
	          CHANGE_NAME IS NOT NULL
	  ORDER
	     BY
	          BOARD_NO DESC;



