package com.little.g.common.error;

/**
 * Created by lengligang on 2019/3/12.
 * 错误码分配边界
 */
public class ErrorCodeDiv {
    /**
     * 通用错误码
     */
    public static  CodeBorder COMMON = new CodeBorder(10000,15000);
    /**
     * DEMO相关错误码
     */
    public static  CodeBorder DEMO = new CodeBorder(20000,25000);

    public static class CodeBorder{

        public CodeBorder(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        private Integer start;

        private Integer end;

        public Integer getStart() {
            return start;
        }

        public Integer getEnd() {
            return end;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("CodeBorder{");
            sb.append("start=").append(start);
            sb.append(", end=").append(end);
            sb.append('}');
            return sb.toString();
        }
    }
}


