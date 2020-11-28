package com.example.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ImageButton button_frontDefrost, button_rearDefrost, button_recycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock;

    private Button button_ac, button_acMax;

    //boolean variables for button
    private Boolean button_frontDefrost_isSelected, button_rearDefrost_isSelected, button_recycle_isSelected,
            button_domeLight_isSelected, button_doorLock_isSelected, button_ac_isSelected, button_acMax_isSelected;

    private ProgressBar fanProgressBar, tempProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare buttons
        button_frontDefrost = this.findViewById(R.id.button_frontDefrost);
        button_rearDefrost = findViewById(R.id.button_rearDefrost);
        button_recycle = findViewById(R.id.button_recycle);
        button_fanUp = findViewById(R.id.button_fanUp);
        button_fanDown = findViewById(R.id.button_fanDown);
        button_tempUp = findViewById(R.id.button_tempUp);
        button_tempDown = findViewById(R.id.button_tempDown);
        button_domeLight = findViewById(R.id.button_domeLight);
        button_doorLock = findViewById(R.id.button_doorLock);

        button_ac = findViewById(R.id.button_ac);
        button_acMax = findViewById(R.id.button_acMax);

        //declare progress bars
        fanProgressBar = (ProgressBar) findViewById(R.id.fanProgressBar);
        tempProgressBar = (ProgressBar) findViewById(R.id.tempProgressBar);

        //set boolean variables
        button_frontDefrost_isSelected = false;
        button_rearDefrost_isSelected = false;
        button_recycle_isSelected = false;
        button_domeLight_isSelected = false;
        button_doorLock_isSelected = false;
        button_ac_isSelected = false;
        button_acMax_isSelected = false;


        //declare button listeners
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_ac_isSelected) {
                    button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_ac.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_ac_isSelected = !button_ac_isSelected;
            }
        });

        button_acMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_acMax_isSelected) {
                    button_acMax.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_acMax_isSelected = !button_acMax_isSelected;
            }
        });

        button_frontDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_frontDefrost_isSelected) {
                    button_frontDefrost.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_frontDefrost.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_frontDefrost_isSelected = !button_frontDefrost_isSelected;
            }
        });

        button_rearDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_rearDefrost_isSelected) {
                    button_rearDefrost.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_rearDefrost.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_rearDefrost_isSelected = !button_rearDefrost_isSelected;
            }
        });

        button_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_recycle_isSelected) {
                    button_recycle.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_recycle.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_recycle_isSelected = !button_recycle_isSelected;
            }
        });

        button_fanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanProgressBar.incrementProgressBy(10);
            }
        });

        button_fanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanProgressBar.incrementProgressBy(-10);
            }
        });

        button_tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempProgressBar.incrementProgressBy(10);
            }
        });

        button_tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempProgressBar.incrementProgressBy(-10);
            }
        });

        button_domeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_domeLight_isSelected) {
                    button_domeLight.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_domeLight.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_domeLight_isSelected = !button_domeLight_isSelected;
            }
        });

        button_doorLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_doorLock_isSelected) {
                    button_doorLock.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                } else {
                    button_doorLock.setBackgroundColor(getResources().getColor(R.color.black));
                }
                button_doorLock_isSelected = !button_doorLock_isSelected;
            }
        });
    }

    public Boolean getButton_frontDefrost_isSelected() {
        return button_frontDefrost_isSelected;
    }

    public void setButton_frontDefrost_isSelected(Boolean button_frontDefrost_isSelected) {
        this.button_frontDefrost_isSelected = button_frontDefrost_isSelected;
    }

    public Boolean getButton_rearDefrost_isSelected() {
        return button_rearDefrost_isSelected;
    }

    public void setButton_rearDefrost_isSelected(Boolean button_rearDefrost_isSelected) {
        this.button_rearDefrost_isSelected = button_rearDefrost_isSelected;
    }

    public Boolean getButton_recycle_isSelected() {
        return button_recycle_isSelected;
    }

    public void setButton_recycle_isSelected(Boolean button_recycle_isSelected) {
        this.button_recycle_isSelected = button_recycle_isSelected;
    }

    public Boolean getButton_domeLight_isSelected() {
        return button_domeLight_isSelected;
    }

    public void setButton_domeLight_isSelected(Boolean button_domeLight_isSelected) {
        this.button_domeLight_isSelected = button_domeLight_isSelected;
    }

    public Boolean getButton_doorLock_isSelected() {
        return button_doorLock_isSelected;
    }

    public void setButton_doorLock_isSelected(Boolean button_doorLock_isSelected) {
        this.button_doorLock_isSelected = button_doorLock_isSelected;
    }

    public Boolean getButton_ac_isSelected() {
        return button_ac_isSelected;
    }

    public void setButton_ac_isSelected(Boolean button_ac_isSelected) {
        this.button_ac_isSelected = button_ac_isSelected;
    }

    public Boolean getButton_acMax_isSelected() {
        return button_acMax_isSelected;
    }

    public void setButton_acMax_isSelected(Boolean button_acMax_isSelected) {
        this.button_acMax_isSelected = button_acMax_isSelected;
    }

    public ProgressBar getFanProgressBar() {
        return fanProgressBar;
    }

    public void setFanProgressBar(ProgressBar fanProgressBar) {
        this.fanProgressBar = fanProgressBar;
    }

    public ProgressBar getTempProgressBar() {
        return tempProgressBar;
    }

    public void setTempProgressBar(ProgressBar tempProgressBar) {
        this.tempProgressBar = tempProgressBar;
    }

}