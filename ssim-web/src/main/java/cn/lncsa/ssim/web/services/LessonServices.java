package cn.lncsa.ssim.web.services;

import cn.lncsa.ssim.web.model.Lesson;
import cn.lncsa.ssim.web.repositories.LessonDAO;
import cn.lncsa.ssim.web.view.LessonTimePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by catten on 2/24/17.
 */
@Service
public class LessonServices {

    private LessonDAO lessonDAO;

    @Autowired
    public void setLessonDAO(LessonDAO lessonDAO) {
        this.lessonDAO = lessonDAO;
    }

    public List<String> querySchoolTerms() {
        return lessonDAO.getTerms();
    }

    public List<Lesson> querySchedule(String termName, String attendClass) {
        return lessonDAO.findAll((root, cq, cb) -> cb.and(
                cb.equal(root.get("term"), termName),
                cb.equal(root.get("attendClass"), attendClass))
        );
    }

    public List<Lesson> querySchedule(String termName, String attendClass, Integer week, List<String> ignoreType) {
        return lessonDAO.findAll((root, cq, cb) -> {
            Predicate predicate = cb.and(
                    cb.equal(root.get("term"), termName),
                    cb.equal(root.get("attendClass"), attendClass)
            );

            if (week != null) predicate = cb.and(
                    predicate,
                    cb.equal(root.get("week"), week)
            );

            if (ignoreType != null && ignoreType.size() > 0) predicate = cb.and(
                    predicate,
                    cb.not(root.get("category").in(ignoreType))
            );

            return predicate;
        });
    }

    public List<Lesson> querySchedule(String termName, String attendClass, Integer week) {
        return lessonDAO.findAll((root, criteriaQuery, cb) -> cb.and(
                cb.equal(root.get("term"), termName),
                cb.equal(root.get("attendClass"), attendClass),
                cb.equal(root.get("week"), week)
        ));
    }

    public Integer queryWeeks(String termName) {
        return lessonDAO.getLatestWeek(termName);
    }

    public List<String> listClassesInTerm(String termName) {
        return lessonDAO.getAttendClassesInTerm(termName);
    }

    public List<String> listClassTypes() {
        return lessonDAO.getTypes();
    }

    public List<String> listClassTypes(String termName){
        return lessonDAO.getTermTypes(termName);
    }

    public List<LessonTimePoint> getLessonTimePoint(String termName, List<String> className, List<Integer> weeks, List<String> ignoreType){
        List<Object[]> list = (ignoreType == null || ignoreType.size() == 0) ?
                lessonDAO.classTimePointList(termName,className,weeks) : lessonDAO.classTimePointList(termName,className,weeks,ignoreType);
        List<LessonTimePoint> timePoints = new ArrayList<>();
        for (Object[] objects : list){
            timePoints.add(new LessonTimePoint((Integer)objects[0], (Integer)objects[1], (Integer)objects[2]));
        }
        return timePoints;
    }
}
