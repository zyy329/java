package com.zyyapp.util.leetcode.tree;

/**
 * 二叉树节点 结构体
 * @author zyy
 * @date 2020-8-5
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int val) { this.val = val; }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
