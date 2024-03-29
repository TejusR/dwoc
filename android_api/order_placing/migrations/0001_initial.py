# Generated by Django 2.2.4 on 2019-11-20 19:05

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Request',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('requester', models.CharField(max_length=50)),
                ('accepter', models.CharField(max_length=50)),
                ('order_list', models.CharField(max_length=150)),
                ('order_time', models.TimeField()),
                ('home_location', models.CharField(max_length=50)),
                ('shop_location', models.CharField(max_length=50)),
            ],
        ),
    ]
