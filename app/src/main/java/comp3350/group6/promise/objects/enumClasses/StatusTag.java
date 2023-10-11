package comp3350.group6.promise.objects.enumClasses;

public enum StatusTag {

    TODO(1), DOING(2), DONE(3);

    private final int status;

    private StatusTag(int status) {
        this.status = status;
    }
}
