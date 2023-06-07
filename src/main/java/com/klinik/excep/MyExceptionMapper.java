package com.klinik.excep;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.klinik.response.BaseResponse;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception>{

    @Override
    public Response toResponse(Exception e) {
        BaseResponse bs = new BaseResponse();
        bs.setCode( 999);
        bs.setMessage(null == e.getMessage() ? "Ошибка" : e.getMessage());
        return Response.ok().entity(bs).build();
    }
}
