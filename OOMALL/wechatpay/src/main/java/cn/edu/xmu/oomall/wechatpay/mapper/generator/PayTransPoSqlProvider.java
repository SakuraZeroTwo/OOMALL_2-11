package cn.edu.xmu.oomall.wechatpay.mapper.generator;

import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPo;
import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPoExample.Criteria;
import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPoExample.Criterion;
import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPoExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public class PayTransPoSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    public String insertSelective(PayTransPo row) {
        SQL sql = new SQL();
        sql.INSERT_INTO("wechatpay_pay_trans");
        
        if (row.getId() != null) {
            sql.VALUES("`id`", "#{id,jdbcType=BIGINT}");
        }
        
        if (row.getSpAppid() != null) {
            sql.VALUES("`sp_appid`", "#{spAppid,jdbcType=VARCHAR}");
        }
        
        if (row.getSpMchid() != null) {
            sql.VALUES("`sp_mchid`", "#{spMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getSubMchid() != null) {
            sql.VALUES("`sub_mchid`", "#{subMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getDescription() != null) {
            sql.VALUES("`description`", "#{description,jdbcType=VARCHAR}");
        }
        
        if (row.getOutTradeNo() != null) {
            sql.VALUES("`out_trade_no`", "#{outTradeNo,jdbcType=VARCHAR}");
        }
        
        if (row.getTimeExpire() != null) {
            sql.VALUES("`time_expire`", "#{timeExpire,jdbcType=TIMESTAMP}");
        }
        
        if (row.getPayerSpOpenid() != null) {
            sql.VALUES("`payer_sp_openid`", "#{payerSpOpenid,jdbcType=VARCHAR}");
        }
        
        if (row.getAmountTotal() != null) {
            sql.VALUES("`amount_total`", "#{amountTotal,jdbcType=INTEGER}");
        }
        
        if (row.getTradeState() != null) {
            sql.VALUES("`trade_state`", "#{tradeState,jdbcType=VARCHAR}");
        }
        
        if (row.getTradeStateDesc() != null) {
            sql.VALUES("`trade_state_desc`", "#{tradeStateDesc,jdbcType=VARCHAR}");
        }
        
        if (row.getSuccessTime() != null) {
            sql.VALUES("`success_time`", "#{successTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    public String selectByExample(PayTransPoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("`id`");
        } else {
            sql.SELECT("`id`");
        }
        sql.SELECT("`sp_appid`");
        sql.SELECT("`sp_mchid`");
        sql.SELECT("`sub_mchid`");
        sql.SELECT("`description`");
        sql.SELECT("`out_trade_no`");
        sql.SELECT("`time_expire`");
        sql.SELECT("`payer_sp_openid`");
        sql.SELECT("`amount_total`");
        sql.SELECT("`transaction_id`");
        sql.SELECT("`trade_state`");
        sql.SELECT("`trade_state_desc`");
        sql.SELECT("`success_time`");
        sql.FROM("wechatpay_pay_trans");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        PayTransPo row = (PayTransPo) parameter.get("row");
        PayTransPoExample example = (PayTransPoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("wechatpay_pay_trans");
        
        if (row.getId() != null) {
            sql.SET("`id` = #{row.id,jdbcType=BIGINT}");
        }
        
        if (row.getSpAppid() != null) {
            sql.SET("`sp_appid` = #{row.spAppid,jdbcType=VARCHAR}");
        }
        
        if (row.getSpMchid() != null) {
            sql.SET("`sp_mchid` = #{row.spMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getSubMchid() != null) {
            sql.SET("`sub_mchid` = #{row.subMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getDescription() != null) {
            sql.SET("`description` = #{row.description,jdbcType=VARCHAR}");
        }
        
        if (row.getOutTradeNo() != null) {
            sql.SET("`out_trade_no` = #{row.outTradeNo,jdbcType=VARCHAR}");
        }
        
        if (row.getTimeExpire() != null) {
            sql.SET("`time_expire` = #{row.timeExpire,jdbcType=TIMESTAMP}");
        }
        
        if (row.getPayerSpOpenid() != null) {
            sql.SET("`payer_sp_openid` = #{row.payerSpOpenid,jdbcType=VARCHAR}");
        }
        
        if (row.getAmountTotal() != null) {
            sql.SET("`amount_total` = #{row.amountTotal,jdbcType=INTEGER}");
        }
        
        if (row.getTransactionId() != null) {
            sql.SET("`transaction_id` = #{row.transactionId,jdbcType=VARCHAR}");
        }
        
        if (row.getTradeState() != null) {
            sql.SET("`trade_state` = #{row.tradeState,jdbcType=VARCHAR}");
        }
        
        if (row.getTradeStateDesc() != null) {
            sql.SET("`trade_state_desc` = #{row.tradeStateDesc,jdbcType=VARCHAR}");
        }
        
        if (row.getSuccessTime() != null) {
            sql.SET("`success_time` = #{row.successTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("wechatpay_pay_trans");
        
        sql.SET("`id` = #{row.id,jdbcType=BIGINT}");
        sql.SET("`sp_appid` = #{row.spAppid,jdbcType=VARCHAR}");
        sql.SET("`sp_mchid` = #{row.spMchid,jdbcType=VARCHAR}");
        sql.SET("`sub_mchid` = #{row.subMchid,jdbcType=VARCHAR}");
        sql.SET("`description` = #{row.description,jdbcType=VARCHAR}");
        sql.SET("`out_trade_no` = #{row.outTradeNo,jdbcType=VARCHAR}");
        sql.SET("`time_expire` = #{row.timeExpire,jdbcType=TIMESTAMP}");
        sql.SET("`payer_sp_openid` = #{row.payerSpOpenid,jdbcType=VARCHAR}");
        sql.SET("`amount_total` = #{row.amountTotal,jdbcType=INTEGER}");
        sql.SET("`transaction_id` = #{row.transactionId,jdbcType=VARCHAR}");
        sql.SET("`trade_state` = #{row.tradeState,jdbcType=VARCHAR}");
        sql.SET("`trade_state_desc` = #{row.tradeStateDesc,jdbcType=VARCHAR}");
        sql.SET("`success_time` = #{row.successTime,jdbcType=TIMESTAMP}");
        
        PayTransPoExample example = (PayTransPoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(PayTransPo row) {
        SQL sql = new SQL();
        sql.UPDATE("wechatpay_pay_trans");
        
        if (row.getSpAppid() != null) {
            sql.SET("`sp_appid` = #{spAppid,jdbcType=VARCHAR}");
        }
        
        if (row.getSpMchid() != null) {
            sql.SET("`sp_mchid` = #{spMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getSubMchid() != null) {
            sql.SET("`sub_mchid` = #{subMchid,jdbcType=VARCHAR}");
        }
        
        if (row.getDescription() != null) {
            sql.SET("`description` = #{description,jdbcType=VARCHAR}");
        }
        
        if (row.getOutTradeNo() != null) {
            sql.SET("`out_trade_no` = #{outTradeNo,jdbcType=VARCHAR}");
        }
        
        if (row.getTimeExpire() != null) {
            sql.SET("`time_expire` = #{timeExpire,jdbcType=TIMESTAMP}");
        }
        
        if (row.getPayerSpOpenid() != null) {
            sql.SET("`payer_sp_openid` = #{payerSpOpenid,jdbcType=VARCHAR}");
        }
        
        if (row.getAmountTotal() != null) {
            sql.SET("`amount_total` = #{amountTotal,jdbcType=INTEGER}");
        }
        
        if (row.getTransactionId() != null) {
            sql.SET("`transaction_id` = #{transactionId,jdbcType=VARCHAR}");
        }
        
        if (row.getTradeState() != null) {
            sql.SET("`trade_state` = #{tradeState,jdbcType=VARCHAR}");
        }
        
        if (row.getTradeStateDesc() != null) {
            sql.SET("`trade_state_desc` = #{tradeStateDesc,jdbcType=VARCHAR}");
        }
        
        if (row.getSuccessTime() != null) {
            sql.SET("`success_time` = #{successTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("`id` = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, PayTransPoExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}