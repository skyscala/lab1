/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query;


import stdof.query.params.Filter;
import stdof.query.params.FilterParam;
import stdof.query.params.SortParam;
import stdof.query.DateFilterCriteria;
import stdof.query.DateRangeFilterCriteria;
import stdof.query.FilterCriteria;
import stdof.query.ListOfStringsFilterCriteria;
import stdof.query.NumberFilterCriteria;
import stdof.query.ObjectFilterCriteria;
import stdof.query.SortCriteria;
import stdof.query.StringFilterCriteria;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import stdof.lang.CommonLogger;
import stdof.lang.DateUtil;


/**
 *
 * @author zeyarh
 */
public class QueryUtil {
    


    private QueryUtil(){}

    private static void sortFilters(List<FilterParam> filters){
        Collections.sort(filters, (FilterParam o1, FilterParam o2) -> {
            if (o1 != null && o2 != null) {
                Integer i1 = (o1.getOrder());
                Integer i2 = (o2.getOrder());
                return i1.compareTo(i2);
            }
            return 0;
        });
    }

    private static FilterCriteria createFilterCriteria(FilterParam filterParam,
             Map<String, String> nameAndAttrMap, SimpleDateFormat sdf){
        String filterType = filterParam.getFilterType();
        FilterCriteria searchCriteria = null;

        if ("text".equalsIgnoreCase(filterType)) {
            StringFilterCriteria s = new StringFilterCriteria();
            s.setValue(filterParam.getTextValue().getValue());
            searchCriteria = s;
        } else if ("date".equalsIgnoreCase(filterType)) {
            try {
                DateFilterCriteria s = new DateFilterCriteria();
                String stringDate = filterParam.getDateValue().getDate();
                Date date = sdf.parse(stringDate);
                s.setDate(date);
                searchCriteria = s;
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid input date.");
            }
        } else if ("dateRange".equalsIgnoreCase(filterType)) {
            try {
                DateRangeFilterCriteria s = new DateRangeFilterCriteria();
                String startDate = filterParam.getDateRangeValue().getStart();
                String endDate = filterParam.getDateRangeValue().getEnd();
                Date date1 = sdf.parse(startDate);
                Date date2 = sdf.parse(endDate);
                s.setStart(date1);
                s.setEnd(date2);
                searchCriteria = s;
            } catch (Exception ex) {
                CommonLogger.log(QueryUtil.class, "Date Range filter params processing error - ", ex);
                throw new IllegalArgumentException("Invalid date range.");
            }
        } else if ("textArray".equalsIgnoreCase(filterType)) {
            ListOfStringsFilterCriteria s = new ListOfStringsFilterCriteria();
            s.setValues(filterParam.getTextArrayValue().getList());
            searchCriteria = s;
        } else if ("number".equalsIgnoreCase(filterType)) {
            NumberFilterCriteria s = new NumberFilterCriteria();
            s.setValue(Long.valueOf(filterParam.getNumberValue().getValue()));
            searchCriteria = s;
        } else if ("object".equalsIgnoreCase(filterType)) {
            ObjectFilterCriteria s = new ObjectFilterCriteria();
            s.setValue(filterParam.getObjectValue());
            searchCriteria = s;
        }

        if(searchCriteria!=null) {
            String k = filterParam.getKey();
            String key = null;
            if (nameAndAttrMap != null) {
                key = nameAndAttrMap.get(k);
            }
            if (key == null) {
                key = k;
            }
            CommonLogger.log(QueryUtil.class, "filterKey:" + key);
            searchCriteria.setKey(key);
            searchCriteria.setOperation(filterParam.getFilterExpression());
        }
        return searchCriteria;
    }

    public static FilterCriteria[] createFilterCriteriaArray(Filter filter, Map<String, String> nameAndAttrMap) {

        
        SimpleDateFormat sdf = DateUtil.createDefaultDateFormatter();

        List<FilterParam> filters = null;

        if (filter != null && filter.getFilterParams() != null) {
            filters = filter.getFilterParams();
        }

        FilterCriteria[] filterCriteriaArray = null;
        if (filters != null) {

            sortFilters(filters);

            List<FilterCriteria> searchCriteriaList = new ArrayList<>();
            for (FilterParam filterParam : filters) {
                FilterCriteria searchCriteria = createFilterCriteria(filterParam,nameAndAttrMap,sdf);
                if(searchCriteria!=null) {
                    searchCriteriaList.add(searchCriteria);
                }
            }
            if (!searchCriteriaList.isEmpty()) {
                filterCriteriaArray = new FilterCriteria[searchCriteriaList.size()];
                searchCriteriaList.toArray(filterCriteriaArray);
            }
        }
        return filterCriteriaArray;
    }

    public static SortCriteria[] createSortCriteriaArray(List<SortParam> sortings, Map<String, String> nameAndAttrMap) {
        SortCriteria[] sortArray = null;
        if (sortings != null) {

            Collections.sort(sortings, (SortParam o1, SortParam o2) -> {
                if (o1 != null && o2 != null) {
                    Integer i1 = (o1.getOrder());
                    Integer i2 = (o2.getOrder());
                    return i1.compareTo(i2);
                }
                return 0;
            });

            List<SortCriteria> list = new ArrayList<>();
            for (SortParam sort : sortings) {
                SortCriteria sortCriteria = new SortCriteria();
                String k = sort.getKey();
                String key = null;
                if (nameAndAttrMap != null) {
                    key = nameAndAttrMap.get(k);
                }
                if (key == null) {
                    key = k;
                }
                sortCriteria.setKey(key);
                sortCriteria.setType(sort.getSortType());
                list.add(sortCriteria);
            }
            if (!list.isEmpty()) {
                sortArray = new SortCriteria[list.size()];
                list.toArray(sortArray);
            }
        }
        return sortArray;
    }
}
