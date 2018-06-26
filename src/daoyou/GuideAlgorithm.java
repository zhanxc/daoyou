package daoyou;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


/**
 *
 * @author geekgao
 * 简单的导游系统
 *
 */

public class GuideAlgorithm {

    /**
     *
     * @param start	��ʼ������
     * @param end	����������
     * @param count	���ĸ���
     * @param map	ͼ
     * @return
     */
    public static Integer[] Dijkstra(int start,int end,int count,Map<Integer,Vertex> map) {
        if (start == end) {
            return null;
        }

        class LengthAndRoad{
            int length;
            List<Integer> road = new LinkedList<Integer>();
        }
        LengthAndRoad[] temp= new LengthAndRoad[count + 1];//�㷨����ʱ�õ����Ǹ�����,�����±���Ӧ�ڵ�����
        Set<Integer> alreadyFind = new HashSet<Integer>();//�Ѿ��ҵ�����·������Щ���ļ���

//		===============================================
//		��ʼ����������
        Vertex tmpVex = map.get(start);
        Set<Integer> point = tmpVex.pointNum.keySet();//������ʼ��ָ������Щ��������
        for (Integer i:point) {
            temp[i] = new LengthAndRoad();
            temp[i].length = tmpVex.pointNum.get(i);
            temp[i].road = new LinkedList<Integer>();
            temp[i].road.add(start);
            temp[i].road.add(i);
        }
        alreadyFind.add(start);
//		===============================================
        int currentStart = 0;//ÿ��ѭ��ʱ��ʼ��������,ѭ����ȥ��һ������ѡ�������㣨·���������̵��Ǹ��㣩
        int MAX = 2147483647;
        for (int k = 1;k <= count;k++) {
            long endTime = System.currentTimeMillis();

            int minLength = MAX;
            int n = -1;//��������temp��������
            for (LengthAndRoad l:temp) {
                n++;
                if (l == null || alreadyFind.contains(n)) {
                    continue;
                }
                if (minLength > l.length) {
                    minLength = l.length;
                    currentStart = n;
                }
            }
            if (currentStart == end) {
                break;
            }
            alreadyFind.add(currentStart);

            Vertex currentVex = map.get(currentStart);//��ǰ��ʼ��
            List<Integer> currentStartRoad = new LinkedList<Integer>(temp[currentStart].road);//��ǰ·��(����ʼ��·�����ƹ���)

            Set<Integer> currentPoint = currentVex.pointNum.keySet();//���õ�ǰ��ָ����Щ��
            for (Integer i:currentPoint) {
                if (alreadyFind.contains(i)) {
                    continue;
                }
                if ((temp[i] == null) || (temp[currentStart].length + currentVex.pointNum.get(i) < temp[i].length)) {
                    LengthAndRoad newRoad = new LengthAndRoad();
                    newRoad.length = temp[currentStart].length + currentVex.pointNum.get(i);
                    newRoad.road = new LinkedList<Integer>(currentStartRoad);
                    newRoad.road.add(i);
                    temp[i] = newRoad;
                }
            }
        }

        if (currentStart != end) {
            return null;
        }

        Integer[] result = new Integer[temp[currentStart].road.size()];
        int n = 0;
        for (Integer i:temp[currentStart].road) {
            result[n++] = i;
        }
        return result;
    }

    public static Integer[] Bfs(int startNum, Map<Integer, Vertex> map,
                                Map<String, String> view, Map<Integer, String> viewNumNameMap) {

        List<Integer> resultList = new LinkedList<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();

        Set<Integer> alreadyFind = new HashSet<Integer>();//�洢�Ѿ����ʹ��ĵ�
        q.offer(startNum);//������ʼ��
        alreadyFind.add(startNum);
        while (!q.isEmpty()) {
            Integer num = q.poll();
            if (viewNumNameMap.containsKey(num)) {
                resultList.add(num);
            }
            Set<Integer> pointNum = map.get(num).pointNum.keySet();
            for (Integer i:pointNum) {
                if (!alreadyFind.contains(i)) {
                    q.offer(i);
                    alreadyFind.add(i);
                }
            }
        }

        Integer[] result = new Integer[resultList.size()];
        for (int i = 0;i < resultList.size();i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    public static Integer[] Dfs(int startNum, Map<Integer, Vertex> map,
                                Map<String, String> view, Map<Integer, String> viewNumNameMap) {

        List<Integer> resultList = new LinkedList<Integer>();//�㷨�洢���ս���������
        Stack<Integer> s = new Stack<Integer>();//�㷨���õ���ջ
        Set<Integer> alreadyFind = new HashSet<Integer>();//�洢�Ѿ����ʹ��ĵ�

        s.push(startNum);
        alreadyFind.add(startNum);
        while (!s.isEmpty()) {
            int num = s.pop();
            if (viewNumNameMap.containsKey(num)) {
                resultList.add(num);
            }
            Map<Integer,Integer> pointNum = map.get(num).pointNum;
            for (Iterator<Integer> it = pointNum.keySet().iterator();it.hasNext();) {
                int nextNum = it.next();
                if (!alreadyFind.contains(nextNum)) {
                    s.push(nextNum);
                    alreadyFind.add(nextNum);
                }
            }
        }
        Integer[] result = new Integer[resultList.size()];
        for (int i = 0;i < resultList.size();i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

}
