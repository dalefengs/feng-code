package cn.lzscxb.common.exception.file;

import cn.lzscxb.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author Likfees
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
