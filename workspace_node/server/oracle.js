const oracle = require('oracledb')

getEmp()

async function getEmp(){

    let conn = null
    try{

        conn = await oracle.getConnection({
            connectString: '125.181.132.133:51521/xe',
            user: 'scott4_11',
            password: 'tiger'
        })
        
        // SQL 준비
        const query = ' select * from emp'

        // SQL 실행 및 결과 확보
        const result = await conn.execute(query, [], {
            outFormat: oracle.OUT_FORMAT_OBJECT
        })

        // 결과 활용
    console.log('result', result)

    } catch(err){
        console.log('>>> ERR >>>', err)
    } finally{
        if(conn){
            try{
                await conn.close()
            }catch(err){
                console.log('>>>>finally > ERR >>>>', err)
            }
        }
    }
}

async function getDeptno(deptno) {
    let conn = null;
    try {
        conn = await oracle.getConnection({
            connectString: '125.181.132.133:51521/xe',
            user: 'scott4_11',
            password: 'tiger'
        });
        
        // SQL 준비 (바인드 변수 :deptno 사용)
        const query = 'select * from emp where deptno = :deptno';

        // SQL 실행 및 결과 확보
        const result = await conn.execute(
            query,
            [deptno],   // ✅ 바인드 값 전달
            { outFormat: oracle.OUT_FORMAT_OBJECT }
        );

        // 결과 활용
        console.log('length', result.rows.length);
        if (result.rows.length > 0) {
            console.log('첫번째', result.rows[0].ENAME);
        }

        return result; // ✅ return 위치 try 안으로 이동
    } catch (err) {
        console.log('>>> ERR >>>', err);
    } finally {
        if (conn) {
            try {
                await conn.close();
            } catch (err) {
                console.log('>>>>finally > ERR >>>>', err);
            }
        }
    }
}

module.exports = {
    getDeptno : getDeptno,
    getEmp,
}