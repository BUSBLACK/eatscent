package com.example.eatscent;

public class Solution {
    public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
             val = x;
              next = null;
          }
      }
    public static ListNode addListNode(ListNode l,int[] arr){
        ListNode l1 = l;
        while(l1.next != null){
                l1 = l1.next;
        }
        for(int i = 0;i < arr.length;i++){
            l1.next = new ListNode(arr[i]);
            l1 = l1.next;
        }
        return l;
    }
    /**
     * 链表相加，采用双指针碰撞，两个指针反复遍历两条来表，最终碰撞出相交的节点
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode ha = headA, hb = headB;
        while (ha != hb) {
            ha = ha != null ? ha.next : headB;
            hb = hb != null ? hb.next : headA;
        }
        return ha;
    }
/*
 *  移除链表中的某个特定值元素,为避免头节点出现，添加一个虚拟节点
 */
    public static ListNode removeElements(ListNode head, int val) {
        ListNode l1 = new ListNode(val-1) ;
        l1.next = head;
        ListNode l2 = l1;
        while (l2.next != null) {
            if(l2.next.val == val){
                l2.next = l2.next.next;
            }else {
                l2 = l2.next;
            }
        }
        return l1.next;
    }
/*
 * 删除某个节点
 */
    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
    /*
     *链表的长度
     */
    public static int sizeListNode(ListNode head){
        ListNode l1 = head;
        int num = 0;
        while(l1 != null){
            num = num+1;
            l1 = l1.next;
        }
        return num;
    }
    /*
     * 链表中的中间节点
     */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 是否为回文链表
     * @param head
     */
    public static boolean isPalindrome(ListNode head) {

        return false;
    }
    public static void  main(String args[]){
        ListNode l1 = new ListNode(1);
        int[] arr = {2,3,4,5,6};
        l1 = addListNode(l1,arr);
        ListNode s1 = middleNode(l1);
        System.out.println("666");
    }
}
