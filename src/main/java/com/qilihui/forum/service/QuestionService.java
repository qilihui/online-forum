package com.qilihui.forum.service;

import com.qilihui.forum.config.RateException;
import com.qilihui.forum.pojo.Question;

import java.text.ParseException;
import java.util.List;

public interface QuestionService {

    /**
     * 新增问题
     * @param question
     * @return
     */
    boolean insertQuestion(Question question, Integer ding) throws RateException;

    /**
     * 查询所有问题信息
     * @return
     */
    List<Question> queryAllQuestionsWithCurrPage(Integer currPage) throws ParseException;

    /**
     * 查询所有问题信息
     * @return
     */
    List<Question> queryAllQuestions();

    /**
     * 获取关注用户的文章
     * @return
     */
    List<Question> getAllQuestionWithFollow(int uid);

    /**
     * 推荐
     * @return
     */
    List<Question> getAllQuestionsByViewCount();

    /**
     * 热门
     * @return
     */
    List<Question> getAllQuestionsByCommentCount();

    /**
     * 消灭0回复
     * @return
     */
    List<Question> getAllQuestionsByZeroComment();

    /**
     * 根据主键查询问题
     * @param id
     * @return
     */
    Question selectByPrimaryKey(Integer id);

    /**
     * 更新阅读量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Question record);

    /**
     * 根据问题ID编辑问题信息
     * @param questionId
     * @param description
     * @param tag
     * @return
     */
    int updateQuestionInfoByIdSelective(Integer questionId, String description, String tag);

    /**
     * 删除问题
     * @param questionId
     * @param userId
     * @return
     */
    int deleteQuestion(Integer questionId, Integer userId);

    /**
     * 根据浏览量查询文章信息
     * @return
     */
    List<Question> selectQuestionInfoByViewCount();

    /**
     * 根据问题Id查询标签信息
     * @param questionId
     * @return
     */
    String queryTagByQuestionId(Integer questionId);

    /**
     * 根据发布者查询发布的问题
     * @param publisherId
     * @return
     */
    List<Question> getAllQuestionsByPublisher(Integer publisherId);

    /**
     * 获取我的最热问题
     * @param publisherId
     * @return
     */
    List<Question> selectMyHotQuestions(Integer publisherId);

    /**
     * 查询论坛推荐问题
     * @return
     */
    List<Question> selectForumRecommendQuestions();

    /**
     * 获取所有精品问题
     * @return 问题信息
     */
    List<Question> getAllJingQuestions();

    /**
     * 更新likeCount
     * @param likeCount
     * @param questionId
     * @return
     */
    int updateLikeCountById(Integer likeCount, Integer questionId);
}
