package com.imooc.passbook.passbook.vo;



import com.google.common.base.Enums;
import com.imooc.passbook.passbook.constant.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.hbase.thirdparty.com.google.common.base.Enums;

/**
 * @Author liforever
 * @Date 2019/3/27 9:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评论类型
     */
    private String type;
    /**
     * PassTemplate RowKey,如果是app类型的评论，则没有
     */
    private String templateId;

    /**
     * 评论内容
     */
    private String comment;

    public boolean validate(){
        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class, this.type.toUpperCase()
        ).orNull();
        return !(null == feedbackType || null == comment);
    }
}
