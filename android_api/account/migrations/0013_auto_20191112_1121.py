# Generated by Django 2.2.4 on 2019-11-12 05:51

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('account', '0012_auto_20191112_1119'),
    ]

    operations = [
        migrations.DeleteModel(
            name='Ride',
        ),
        migrations.DeleteModel(
            name='RideSetup',
        ),
    ]
