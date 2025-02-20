package alingchasan.queue;

/**
 * 对顶堆：就是使用双堆(左边是大根堆，右边是小跟堆)，同时大根堆的元素要都小于小跟堆的元素
 * 性质：
 * 1、左边 堆的元素要都小于 右边堆的元素，从左到右 是递增模式
 * 2、左边堆的元素个数>右边个数+1
 *
 * 方法：insert、get、balance（平衡） 方法
 *
 * 其他想法：类似这种 需要获取中间值、或者支持在前中后插入的等(设计前中后队列 1670)。我们优先考虑 分多集合进行处理
 */
public class DuiDingHeap {
}
