package com.zyyapp.util.leetcode.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.*;

/**
 * 二叉树 工具
 * 随机测试用例生成工具
 * 根据传入字符串参数, 解析生成二叉树;
 * 树型信息打印;
 * @author zyy
 * @date 2020-8-19
 */
public class UtilTreeNode {
    /** 测试数据随机生成器 */
    public static String genTestTree() {
        Random r = new Random();

        int size = r.nextInt(20);
        if (size == 0) {
            return "[]";
        }
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            if (Math.random() < 0.2) {
                // empty
                arr[i] = null;
                continue;
            }
            arr[i] = r.nextInt(100);
        }

        return JSON.toJSONString(arr);
    }

    /** 根据传入字符串, 建立 Tree */
    public static TreeNode buildTree(String param) {
        JSONArray arr = JSON.parseArray(param);

        // 前置判断;
        Object frist;
        if (arr.isEmpty() || (frist = arr.get(0)) == null) {
            return new TreeNode(0);
        }

        TreeNode root = new TreeNode((int)frist);

        // 树型构建辅助队列;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);

        // 构建树;
        TreeNode curPar;
        int arrIdx = 1;
        while ((curPar = nodeQueue.poll()) != null) {
            try {
                Object left = arr.get(arrIdx);
                if (left != null) {
                    curPar.left = new TreeNode((int)left);
                    nodeQueue.offer(curPar.left);
                }
                Object right = arr.get(arrIdx+1);
                if (right != null) {
                    curPar.right = new TreeNode((int)right);
                    nodeQueue.offer(curPar.right);
                }
                arrIdx += 2;
            } catch (IndexOutOfBoundsException e) {
                // 已经处理完所有传入数据;
                break;
            }
        }

        return root;
    }

    /** 可视化输出 树 */
    public static void printTree(TreeNode root) {
        List<List<Integer>> arr = new ArrayList<>();
        Queue<TreeNode> curQue = new LinkedList<>();
        Queue<TreeNode> nextQue = new LinkedList<>();
        Queue<TreeNode> temp;
        TreeNode node;
        // 当前层非null节点数量;
        int validNum = 1;
        curQue.offer(root);

        // 广度遍历, 一层一层的构建节点队列;
        while (validNum > 0) {
            // 清空, 以便统计下一层的有效数量;
            validNum = 0;

            // 本层队列;
            List<Integer> curLevLst = new ArrayList<>();
            arr.add(curLevLst);

            while (!curQue.isEmpty()) {
                node = curQue.poll();

                // 当前节点加入本层队列;
                if (node == null) {
                    curLevLst.add(null);

                    // 构建下一层的节点;
                    nextQue.offer(null);
                    nextQue.offer(null);
                } else {
                    curLevLst.add(node.val);

                    // 构建下一层的节点;
                    nextQue.offer(node.left);
                    nextQue.offer(node.right);

                    if (node.left != null) {
                        validNum++;
                    }
                    if (node.right != null) {
                        validNum++;
                    }
                }
            }

            // 交换;
            temp = curQue;
            curQue = nextQue;
            nextQue = temp;
            nextQue.clear();
        }

        // 反向树输出;
        String halfGap = "  ";
        StringBuilder sb = new StringBuilder();
        for (int i = arr.size()-1, revIdx = 0; i >= 0; i--, revIdx++) {
            List<Integer> lev = arr.get(i);
            // 每个节点占据的输出单元格子数;
            int ceilNum = (int)Math.pow(2, revIdx);
            int halfGapNum = (ceilNum - 1);

            // 输出;
            sb.setLength(0);
            for (Integer val : lev) {
                for (int j = 0; j < halfGapNum; j++) {
                    sb.append(halfGap);
                }

                if (val == null) {
                    sb.append("[  ]");
                } else {
                    sb.append(String.format("[%2d]", val));
                }

                for (int j = 0; j < halfGapNum; j++) {
                    sb.append(halfGap);
                }
            }
            System.out.println(sb.toString());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
