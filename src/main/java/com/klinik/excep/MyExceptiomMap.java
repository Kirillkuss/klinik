package com.klinik.excep;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.klinik.response.BaseResponse;

@Provider
public class MyExceptiomMap implements ExceptionMapper<MyException>{

    @Override
    public Response toResponse(MyException e) {
        BaseResponse bs = new BaseResponse();
        bs.setCode(e.getCode() == 0 ? 999 : e.getCode());
        bs.setMassage(null == e.getMessage() ? "Ошибка" : e.getMessage());
        return Response.status( e.getCode() == 0 ? 200 : e.getCode() ).entity(bs).build();
    }
    
}
