#include "AVLTree.h"

template <typename T>
void Delete(AVLNode<T> *&node)
{
    if (node != NULL)
    {
        Delete(node->left);
        Delete(node->right);

        delete node;
        node = NULL;
    }
}

template <typename T>
void RotateRight(AVLNode<T> *&node)
{
    AVLNode<T> *x = node;
    AVLNode<T> *y = node->left;

    x->left = y->right;
    y->right = x;
    node = y;

    x->balance += 1;
    if (y->balance < 0) x->balance += -y->balance;

    y->balance += 1;
    if (x->balance > 0) y->balance += x->balance;
}

template <typename T>
void RotateLeft(AVLNode<T> *&node)
{
    AVLNode<T> *x = node;
    AVLNode<T> *y = node->right;

    x->right = y->left;
    y->left  = x;
    node = y;

    x->balance -= 1;
    if (y->balance > 0) x->balance -= y->balance;

    y->balance -= 1;
    if (x->balance < 0) y->balance -= -x->balance;
}

template <typename T>
bool IncrementBalance(AVLNode<T> *&node)
{
    node->balance++;

    if (node->balance == 0)
        return false;

    if (node->balance == 1)
        return true;

    if (node->balance == 2)
    {
        if (node->right->balance == -1)
            RotateRight(node->right);

        RotateLeft(node);

        return false;
    }

    // ERROR: initially non-balanced node
    return true;
}

template <typename T>
bool DecrementBalance(AVLNode<T> *&node)
{
    node->balance--;

    if (node->balance == 0)
        return false;

    if (node->balance == -1)
        return true;

    if (node->balance == -2)
    {
        if (node->left->balance == 1)
            RotateLeft(node->left);

        RotateRight(node);

        return false;
    }

    // ERROR: initially non-balanced node
    return true;
}

template <typename T>
bool AVLNode<T>::Add(T item, AVLNode<T> *&node)
{
    if (node == NULL)
    {
        node = new AVLNode<T>(item);
        return true;
    }
    else if (item > node->value)
    {
        return Add(item, node->right) && IncrementBalance(node);
    }
    else if(item < node->value)
    {
        return Add(item, node->left) && DecrementBalance(node);
    }

    return false; // no need to add already existing node
}

template <typename T>
bool AVLNode<T>::Remove(T item, AVLNode<T> *&node)
{
    if (node == NULL)
    {
        return false;
    }
    else if (node->value > item)
    {
        return Remove(item, node->left) && IncrementBalance(node);
    }
    else if (node->value < item)
    {
        return Remove(item, node->right) && DecrementBalance(node);
    }
    else // if (node->value == item)
    {
        if (node->left == NULL && node->right == NULL)
        {
            Delete(node);
        }
        else if (node->left != NULL && node->right != NULL)
        {
            // NOTE: "node" is reference to pointer, that can be
            //  changed during new remove. It is necessary to
            //  create another local pointer that cannot be changed
            //  form outside, to point current node.
            AVLNode<T> *current_node = node;
            T min_node_value = Min(node->right);

            bool isHeightChanged = Remove(min_node_value, node);
            current_node->value = min_node_value;

            return isHeightChanged;
        }
        else
        {
            AVLNode<T> *node_to_remove = node;

            if (node->left == NULL)
            {
                node = node_to_remove->right;
                node_to_remove->right = NULL;
            }
            else
            {
                node = node_to_remove->left;
                node_to_remove->left = NULL;
            }

            Delete(node_to_remove);
        }

        return true;
    }
}

template <typename T>
int AVLNode<T>::Max(const AVLNode<T> *const node)
{
    return node->right == NULL ? node->value : Max(node->right);
}

template <typename T>
int AVLNode<T>::Min(const AVLNode<T> *const node)
{
    return node->left == NULL ? node->value : Min(node->left);
}
